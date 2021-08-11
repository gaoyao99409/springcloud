package com.springcloud.jbsdemo.service.cal;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptRoomBO;
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
public class RoomDfsCal {

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
        for (ScriptRoomBO scriptRoomBO : jbsOrderBO.getScript().getScriptRoomList()) {
            if (roomLockMap.containsKey(scriptRoomBO.getRoomId())) {
                continue;
            }
            if (canSelect(jbsOrderBO, scriptRoomBO)) {
                scriptRoomBO.setSelected(true);
                jbsOrderBO.setRoomId(scriptRoomBO.getRoomId());
                roomUsedMap.get(scriptRoomBO.getRoomId()).add(jbsOrderBO);
                return true;
            } else {
                roomLockMap.put(scriptRoomBO.getRoomId(), true);
                try {
                    List<JbsOrderBO> conflictOrderList = getConflictOrders(jbsOrderBO, scriptRoomBO.getRoomId());
                    //备份roomUsedMap
                    Map<Long, List<JbsOrderBO>> roomUsedMapBak = backUpRoomUsedMap(roomUsedMap);

                    if (conflictOrderList.size() > 0 && findOrdersRoom(conflictOrderList)) {
                        //把冲突订单的此room选择去掉
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
                        roomUsedMap = roomUsedMapBak;
                        //恢复订单
                        restoreOrderByRoomUsedMap(jbsOrderBOList, roomUsedMap);
                    }
                } finally {
                    roomLockMap.remove(scriptRoomBO.getRoomId());
                }
            }
        }
        return false;

    }

    /**
     * 根据map恢复订单里的selected
     * @param orderList
     * @param map
     */
    public static void restoreOrderByRoomUsedMap(List<JbsOrderBO> orderList, Map<Long, List<JbsOrderBO>> map) {
        for (JbsOrderBO order : orderList) {
            for (ScriptRoomBO room : order.getScript().getScriptRoomList()) {
                room.setSelected(false);
                if (map.containsKey(room.getRoomId()) && map.get(room.getRoomId()).contains(order)) {
                    room.setSelected(true);
                }
            }
        }
    }

    /**
     * 新建一个备份map保存roomUsedMap备份信息
     * @return
     * @param bakMap
     */
    public static Map<Long, List<JbsOrderBO>> backUpRoomUsedMap(Map<Long, List<JbsOrderBO>> bakMap) {
        Map<Long, List<JbsOrderBO>> bak = Maps.newHashMapWithExpectedSize(bakMap.size());
        for (Long key : bakMap.keySet()) {
            bak.put(key, Lists.newArrayList(bakMap.get(key)));
        }
        return bak;
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
