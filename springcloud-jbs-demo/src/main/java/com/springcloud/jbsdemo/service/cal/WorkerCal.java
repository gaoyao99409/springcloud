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
    Map<Long, Boolean> workerLockMap = Maps.newHashMap();

    public void find(List<JbsOrderBO> jbsOrderBOList) {
        workerUsedMap.clear();
        for (JbsOrderBO jbsOrderBO : jbsOrderBOList) {
            addWorkerUsedMap(jbsOrderBO);
            findOrderRole(jbsOrderBO);
        }
    }

    /**
     * 初始化workerUsedMap
     * @param jbsOrderBO
     */
    private void addWorkerUsedMap(JbsOrderBO jbsOrderBO) {
        for (ScriptWorkerRoleBO scriptWorkerRoleBO : jbsOrderBO.getScript().getScriptWorkerRoleList()) {
            for (ScriptWorker scriptWorker : scriptWorkerRoleBO.getScriptWorkerList()) {
                //todo 此处初始化 后续可以把已经分好的订单数据加入到workerUsedMap中。 只有新订单没有worker数据
                if (!workerUsedMap.containsKey(scriptWorker.getWorkerId())) {
                    workerUsedMap.put(scriptWorker.getWorkerId(), Lists.newArrayList());
                }
            }
        }
    }

    public void findOrderRole(JbsOrderBO jbsOrderBO){
        for(ScriptWorkerRoleBO scriptWorkerRole : jbsOrderBO.getScript().getScriptWorkerRoleList()) {
            findRoleWorker(jbsOrderBO, scriptWorkerRole);
        }
        //todo 如果有未找到的，释放占用的资源，至于占用别人不能完全恢复之前状态问题 后面再说
    }

    /**
     * 查找此role的可匹配worker
     * @param scriptWorkerRole
     * @return
     */
    public boolean findRoleWorker(JbsOrderBO jbsOrderBO, ScriptWorkerRoleBO scriptWorkerRole){
        //todo ScriptWorkerList后续会有动态计算排序 平衡
        for (ScriptWorkerBO scriptWorkerBO : scriptWorkerRole.getScriptWorkerList()) {
            //已被占用
            if (workerLockMap.containsKey(scriptWorkerBO.getWorkerId())) {
                continue;
            }

            /**
             * 此worker可以被选
             */
            if (canBeSelectBy(jbsOrderBO, scriptWorkerBO)) {
                scriptWorkerBO.setSelected(true);
                scriptWorkerBO.getHasSelectedOrderList().add(jbsOrderBO);
                workerUsedMap.get(scriptWorkerBO.getWorkerId()).add(jbsOrderBO);
                return true;
            } else {
                /**
                 * 此worker已经被选
                 * 抢占worker
                 * 让被抢的order去找其他worker
                 * 如果可以找到的话，此订单就用这个worker
                 * todo 此处获取冲突订单，可能出现同时与2个以上订单冲突的情况，后续改进
                 */
                workerLockMap.put(scriptWorkerBO.getWorkerId(), true);
                try {
                    JbsOrderBO conflictOrder = getConflictOrder(scriptWorkerBO.getWorkerId(), jbsOrderBO);
                    if (conflictOrder != null) {
                        ScriptWorkerRoleBO conflictScriptWorkerRole = getScriptWorkerRoleByRole(conflictOrder, scriptWorkerRole.getRole());
                        if (conflictScriptWorkerRole != null && findRoleWorker(conflictOrder, conflictScriptWorkerRole)) {
                            scriptWorkerBO.setSelected(true);
                            scriptWorkerBO.getHasSelectedOrderList().add(jbsOrderBO);
                            cancleSelect(conflictScriptWorkerRole, scriptWorkerBO.getWorkerId());
                            workerUsedMap.get(scriptWorkerBO.getWorkerId()).add(jbsOrderBO);
                            workerUsedMap.get(scriptWorkerBO.getWorkerId()).remove(conflictOrder);
                            return true;
                        }
                    }
                } finally {
                    workerLockMap.remove(scriptWorkerBO.getWorkerId());
                }
            }
        }
        return false;

    }

    private void cancleSelect(ScriptWorkerRoleBO conflictScriptWorkerRole, Long workerId) {
        for (ScriptWorkerBO scriptWorkerBO : conflictScriptWorkerRole.getScriptWorkerList()) {
            if (scriptWorkerBO.getWorkerId().equals(workerId)) {
                scriptWorkerBO.setSelected(false);
                return;
            }
        }
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

    public JbsOrderBO getConflictOrder(Long workerId, JbsOrderBO jbsOrderBO) {
        JbsOrderBO confliectOrder = null;
        for (JbsOrderBO order : workerUsedMap.get(workerId)) {
            if (DateUtil.isDateOverlapping(order.getBeginTime(), order.getEndTime(), jbsOrderBO.getBeginTime(), jbsOrderBO.getEndTime())) {
                confliectOrder = order;
                break;
            }
        }
        return confliectOrder;
    }

    /**
     * 此worker可以被jbsOrderBO选择
     * @param jbsOrderBO
     * @param scriptWorkerBO
     * @return
     */
    public boolean canBeSelectBy(JbsOrderBO jbsOrderBO, ScriptWorkerBO scriptWorkerBO){
        List<JbsOrderBO> jbsOrderBOList = workerUsedMap.get(scriptWorkerBO.getWorkerId());
        if (jbsOrderBOList == null) {
            return true;
        }
        for (JbsOrderBO order : jbsOrderBOList) {
            if (DateUtil.isDateOverlapping(order.getBeginTime(), order.getEndTime(), jbsOrderBO.getBeginTime(), jbsOrderBO.getEndTime())) {
                return false;
            }
        }
        return true;
    }

}
