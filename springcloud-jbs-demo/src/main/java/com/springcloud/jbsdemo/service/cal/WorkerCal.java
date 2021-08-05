package com.springcloud.jbsdemo.service.cal;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerRoleBO;
import com.springcloud.jbsdemo.model.ScriptWorker;
import org.springframework.stereotype.Service;

/**
 * @ClassName WorkerCal
 * @Description WorkerCal
 * @Author gaoyao
 * @Date 2021/8/4 10:16 AM
 * @Version 1.0
 */
@Service
public class WorkerCal {

    Map<Long, List<JbsOrderBO>> workerUsedMap = Maps.newHashMap();

    public void find(List<JbsOrderBO> jbsOrderBOList) {
        for (JbsOrderBO jbsOrderBO : jbsOrderBOList) {
            addWorkerUsedMap(jbsOrderBO);
            new WorkerCal().findOrderRole(jbsOrderBO);
        }
    }

    private void addWorkerUsedMap(JbsOrderBO jbsOrderBO) {
        for (ScriptWorkerRoleBO scriptWorkerRoleBO : jbsOrderBO.getScript().getScriptWorkerRoleList()) {
            for (ScriptWorker scriptWorker : scriptWorkerRoleBO.getScriptWorkerList()) {
                workerUsedMap.put(scriptWorker.getId(), null);
            }
        }
    }

    public void findOrderRole(JbsOrderBO jbsOrderBO){
        for(ScriptWorkerRoleBO scriptWorkerRole : jbsOrderBO.getScript().getScriptWorkerRoleList()) {
            findRoleWorker(jbsOrderBO, scriptWorkerRole);
        }
    }

    /**
     * 查找此role的可匹配worker
     * @param scriptWorkerRole
     * @return
     */
    public boolean findRoleWorker(JbsOrderBO jbsOrderBO, ScriptWorkerRoleBO scriptWorkerRole){
        for (ScriptWorkerBO scriptWorkerBO : scriptWorkerRole.getScriptWorkerList()) {
            /**
             * 此worker可以被选
             */
            if (scriptWorkerBO.canBeSelectBy(jbsOrderBO)) {
                scriptWorkerBO.setSelected(true);
                scriptWorkerBO.getHasSelectedOrderList().add(jbsOrderBO);
                return true;
            } else {
                /**
                 * 此worker已经被选
                 * 抢占worker
                 * 让被抢的order去找其他worker
                 * 如果可以找到的话，此订单就用这个worker
                 */
                JbsOrderBO conflictOrder = getConflictOrder(scriptWorkerBO.getId(), jbsOrderBO);
                if (conflictOrder != null) {
                    ScriptWorkerRoleBO conflictScriptWorkerRole = getScriptWorkerRoleByRole(conflictOrder, scriptWorkerRole.getRole());
                    if (conflictScriptWorkerRole != null && findRoleWorker(conflictOrder, conflictScriptWorkerRole)) {
                        scriptWorkerBO.setSelected(true);
                        scriptWorkerBO.getHasSelectedOrderList().add(jbsOrderBO);
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * 获取订单role的worker成员组
     * @param role
     * @return
     */
    private ScriptWorkerRoleBO getScriptWorkerRoleByRole(JbsOrderBO jbsOrder, String role) {
        for (ScriptWorkerRoleBO scriptWorkerRoleBO : jbsOrder.getScript().getScriptWorkerRoleList()) {
            if (scriptWorkerRoleBO.getRole().equals(role)) {
                return scriptWorkerRoleBO;
            }
        }
        return null;
    }

    public JbsOrderBO getConflictOrder(Long id, JbsOrderBO jbsOrderBO) {
        JbsOrderBO confliectOrder = null;
        for (JbsOrderBO order : workerUsedMap.get(id)) {
            if (DateUtil.isDateOverlapping(order.getBeginTime(), order.getEndTime(), jbsOrderBO.getBeginTime(), jbsOrderBO.getEndTime())) {
                confliectOrder = order;
                break;
            }
        }
        return confliectOrder;
    }

}
