package com.springcloud.jbsdemo.service.cal;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptRoomBO;
import org.springframework.stereotype.Component;

/**
 * @ClassName RoomCal
 * @Description RoomCal
 * @Author gaoyao
 * @Date 2021/8/4 10:16 AM
 * @Version 1.0
 */
@Component
public class RoomCal {

    Map<Long, List<JbsOrderBO>> roomUsedMap = Maps.newHashMap();
    Map<Long, Boolean> roomLockMap = Maps.newHashMap();

    public void find(List<JbsOrderBO> jbsOrderBOList) {
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
                    JbsOrderBO conflictOrder = getConflictOrder(jbsOrderBO, scriptRoomBO.getRoomId());
                    Long bakRoomId = conflictOrder.getRoomId();
                    if (conflictOrder != null && findOrderRoom(conflictOrder)) {
                        ScriptRoomBO conflictRoom = getConflictRoom(conflictOrder, scriptRoomBO.getRoomId());
                        if (conflictRoom != null)
                            conflictRoom.setSelected(false);
                        scriptRoomBO.setSelected(true);
                        jbsOrderBO.setRoomId(scriptRoomBO.getRoomId());
                        roomUsedMap.get(scriptRoomBO.getRoomId()).add(jbsOrderBO);

                        return true;
                    } else {
                        conflictOrder.setRoomId(bakRoomId);
                    }
                } finally {
                    roomLockMap.remove(scriptRoomBO.getRoomId());
                }
            }
        }
        return false;
    }

    private ScriptRoomBO getConflictRoom(JbsOrderBO jbsOrderBO, Long roomId) {
        for (ScriptRoomBO scriptRoomBO : jbsOrderBO.getScript().getScriptRoomList()) {
            if (scriptRoomBO.getRoomId().equals(roomId)) {
                return scriptRoomBO;
            }
        }
        return null;
    }

    private JbsOrderBO getConflictOrder(JbsOrderBO jbsOrderBO, Long roomId) {
        for (JbsOrderBO usedOrder : roomUsedMap.get(roomId)) {
            if (DateUtil.isDateOverlapping(jbsOrderBO.getBeginTime(), jbsOrderBO.getEndTime(), usedOrder.getBeginTime(), usedOrder.getEndTime())) {
                return usedOrder;
            }
        }
        return null;
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
