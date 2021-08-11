package com.springcloud.jbsdemo.service.cal;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptRoomBO;
import com.springcloud.jbsdemo.mapper.RoomMapper;
import com.springcloud.jbsdemo.model.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName RoomCal
 * @Description RoomCal
 * @Author gaoyao
 * @Date 2021/8/4 10:16 AM
 * @Version 1.0
 */
@Component
@Slf4j
public class RoomBfsCal {

    Map<Long, List<JbsOrderBO>> roomUsedMap = Maps.newHashMap();
    Map<Long, Boolean> roomLockMap = Maps.newHashMap();
    List<JbsOrderBO> jbsOrderBOList;

    public void find(List<JbsOrderBO> jbsOrderBOList) {
        this.jbsOrderBOList = jbsOrderBOList;
        roomUsedMap.clear();
        for (JbsOrderBO jbsOrderBO : jbsOrderBOList) {
            addRoomUsedMap(jbsOrderBO);

            if (jbsOrderBO.getRoomId() != null) {
                continue;
            }

            findOrderRoom(jbsOrderBO);
        }
    }

    private void addRoomUsedMap(JbsOrderBO jbsOrderBO) {
        for (ScriptRoomBO scriptRoomBO : jbsOrderBO.getScript().getScriptRoomList()) {
            if (!roomUsedMap.containsKey(scriptRoomBO.getRoomId())) {
                roomUsedMap.put(scriptRoomBO.getRoomId(), Lists.newArrayList());
            }
        }
        if (jbsOrderBO.getRoomId() != null) {
            roomUsedMap.get(jbsOrderBO.getRoomId()).add(jbsOrderBO);
        }
    }

    private boolean findOrderRoom(JbsOrderBO jbsOrderBO) {
        //先找合适的
        for (ScriptRoomBO scriptRoomBO : jbsOrderBO.getScript().getScriptRoomList()) {
            if (roomLockMap.containsKey(scriptRoomBO.getRoomId())) {
                continue;
            }
            if (canSelect(jbsOrderBO, scriptRoomBO)) {
                scriptRoomBO.setSelected(true);
                jbsOrderBO.setRoomId(scriptRoomBO.getRoomId());
                roomUsedMap.get(scriptRoomBO.getRoomId()).add(jbsOrderBO);
                return true;
            }
        }
        //没有合适的 开始挨个抢
        for (ScriptRoomBO scriptRoomBO : jbsOrderBO.getScript().getScriptRoomList()) {
            if (roomLockMap.containsKey(scriptRoomBO.getRoomId())) {
                continue;
            }
            roomLockMap.put(scriptRoomBO.getRoomId(), true);
            try {
                //备份roomUsedMap 当查找失败时，恢复roomUsedMap的数据就可以了
                List<JbsOrderBO> conflictOrderList = getConflictOrders(jbsOrderBO, scriptRoomBO.getRoomId());
                Map<Long, List<JbsOrderBO>> bak = RoomDfsCal.backUpRoomUsedMap(roomUsedMap);
                if (conflictOrderList.size() > 0 && findOrdersRoom(conflictOrderList)) {
                    setConflictOrdersRoomNotSelect(conflictOrderList, scriptRoomBO.getRoomId());
                    scriptRoomBO.setSelected(true);
                    jbsOrderBO.setRoomId(scriptRoomBO.getRoomId());
                    roomUsedMap.get(scriptRoomBO.getRoomId()).add(jbsOrderBO);
                    //删除原占用
                    for (JbsOrderBO bo : conflictOrderList) {
                        roomUsedMap.get(scriptRoomBO.getRoomId()).remove(bo);
                    }
                    return true;
                } else {
                    roomUsedMap = bak;
                    RoomDfsCal.restoreOrderByRoomUsedMap(jbsOrderBOList, roomUsedMap);
                }
            } finally {
                roomLockMap.remove(scriptRoomBO.getRoomId());
            }
        }

        return false;

    }

    private void setConflictOrdersRoomNotSelect(List<JbsOrderBO> conflictOrderList, Long roomId) {
        for (JbsOrderBO order : conflictOrderList) {
            ScriptRoomBO conflictRoom = getConflictRoom(order, roomId);
            if (conflictRoom != null) {
                conflictRoom.setSelected(false);
            }
        }
    }

    private boolean findOrdersRoom(List<JbsOrderBO> conflictOrderList) {
        for (JbsOrderBO bo : conflictOrderList) {
            if (!findOrderRoom(bo)) {
                return false;
            }
        }
        return true;
    }

    private ScriptRoomBO getConflictRoom(JbsOrderBO jbsOrderBO, Long roomId) {
        for (ScriptRoomBO scriptRoomBO : jbsOrderBO.getScript().getScriptRoomList()) {
            if (scriptRoomBO.getRoomId().equals(roomId) && scriptRoomBO.getSelected()) {
                return scriptRoomBO;
            }
        }
        return null;
    }

    private List<JbsOrderBO> getConflictOrders(JbsOrderBO jbsOrderBO, Long roomId) {
        List<JbsOrderBO> list = Lists.newArrayList();
        for (JbsOrderBO usedOrder : roomUsedMap.get(roomId)) {
            if (DateUtil.isDateOverlapping(jbsOrderBO.getBeginTime(), jbsOrderBO.getEndTime(), usedOrder.getBeginTime(), usedOrder.getEndTime())) {
                list.add(usedOrder);
            }
        }
        return list;
    }

    private boolean canSelect(JbsOrderBO jbsOrderBO, ScriptRoomBO scriptRoomBO) {
        for (JbsOrderBO usedOrder : roomUsedMap.get(scriptRoomBO.getRoomId())) {
            if (DateUtil.isDateOverlapping(jbsOrderBO.getBeginTime(), jbsOrderBO.getEndTime(), usedOrder.getBeginTime(), usedOrder.getEndTime())) {
                return false;
            }
        }
        return true;
    }

}
