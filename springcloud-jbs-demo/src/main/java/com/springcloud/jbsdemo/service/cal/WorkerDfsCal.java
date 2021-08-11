package com.springcloud.jbsdemo.service.cal;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerRoleBO;
import com.springcloud.jbsdemo.model.ScriptWorker;
import com.springcloud.jbsdemo.model.Worker;
import com.springcloud.jbsdemo.model.WorkerTime;
import com.springcloud.jbsdemo.service.worker.WorkerTimeService;
import org.springframework.stereotype.Service;

/**
 * @ClassName WorkerCal
 * @Description WorkerCal
 * @Author gaoyao
 * @Date 2021/8/4 10:16 AM
 * @Version 1.0
 */
@Service
public class WorkerDfsCal {

    @Resource
    WorkerTimeService workerTimeService;

    Map<Long, List<JbsOrderBO>> workerUsedMap = Maps.newHashMap();
    Map<Long, Boolean> workerLockMap = Maps.newHashMap();
    Map<Long, String> workerTimeMap = Maps.newHashMap();
    List<JbsOrderBO> jbsOrderBOList;

    public void find(List<JbsOrderBO> jbsOrderBOList) {
        this.jbsOrderBOList = jbsOrderBOList;
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
            if (scriptWorkerRole.selected()) {
                continue;
            }
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

            //worker有时间
            if (!hasTime(jbsOrderBO, scriptWorkerBO)) {
                continue;
            }

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
                 */
                workerLockMap.put(scriptWorkerBO.getWorkerId(), true);
                try {
                    Map<Long, List<JbsOrderBO>> bak = RoomDfsCal.backUpRoomUsedMap(workerUsedMap);
                    List<JbsOrderBO> conflictOrderList = getConflictOrders(scriptWorkerBO.getWorkerId(), jbsOrderBO);
                    if (conflictOrderList.size() > 0 && findOrdersRoleWorker(conflictOrderList, scriptWorkerBO.getWorkerId())) {
//                      ScriptWorkerRoleBO conflictScriptWorkerRole = getScriptWorkerRoleByRole(conflictOrder, scriptWorkerBO.getWorkerId());
                        scriptWorkerBO.setSelected(true);
                        scriptWorkerBO.getHasSelectedOrderList().add(jbsOrderBO);
                        cancleSelect(conflictOrderList, scriptWorkerBO.getWorkerId());
                        workerUsedMap.get(scriptWorkerBO.getWorkerId()).add(jbsOrderBO);
                        for (JbsOrderBO order : conflictOrderList) {
                            workerUsedMap.get(scriptWorkerBO.getWorkerId()).remove(order);
                        }

                        return true;
                    } else {
                        //恢复之前的选择状态
                        workerUsedMap = bak;
                        WorkerBfsCal.restoreOrderByUsedMap(this.jbsOrderBOList, workerUsedMap);
                    }
                } finally {
                    workerLockMap.remove(scriptWorkerBO.getWorkerId());
                }
            }
        }
        return false;

    }

    /**
     * 查找多个冲突订单
     * @param conflictOrderList
     * @param workerId
     * @return
     */
    private boolean findOrdersRoleWorker(List<JbsOrderBO> conflictOrderList, Long workerId) {
        for (JbsOrderBO conflictOrder : conflictOrderList) {
            ScriptWorkerRoleBO conflictScriptWorkerRole = getScriptWorkerRoleByRole(conflictOrder, workerId);
            if (!findRoleWorker(conflictOrder, conflictScriptWorkerRole)) {
                return false;
            }
        }
        return true;
    }

    private List<JbsOrderBO> getConflictOrders(Long workerId, JbsOrderBO jbsOrderBO) {
        List<JbsOrderBO> confliectOrderList = Lists.newArrayList();
        for (JbsOrderBO order : workerUsedMap.get(workerId)) {
            if (DateUtil.isDateOverlapping(order.getBeginTime(), order.getEndTime(), jbsOrderBO.getBeginTime(), jbsOrderBO.getEndTime())) {
                confliectOrderList.add(order);
            }
        }
        return confliectOrderList;
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

    private void cancleSelect(List<JbsOrderBO> conflictOrderList, Long workerId) {
        for (JbsOrderBO conflictOrder : conflictOrderList) {
            ScriptWorkerRoleBO conflictScriptWorkerRole = getScriptWorkerRoleByRole(conflictOrder, workerId);
            for (ScriptWorkerBO scriptWorkerBO : conflictScriptWorkerRole.getScriptWorkerList()) {
                if (scriptWorkerBO.getWorkerId().equals(workerId)) {
                    scriptWorkerBO.setSelected(false);
                    break;
                }
            }
        }

    }

    /**
     * 获取订单role的worker成员组
     * @param workerId
     * @return
     */
    private ScriptWorkerRoleBO getScriptWorkerRoleByRole(JbsOrderBO jbsOrder, Long workerId) {
        for (ScriptWorkerRoleBO scriptWorkerRoleBO : jbsOrder.getScript().getScriptWorkerRoleList()) {
            for (ScriptWorkerBO scriptWorkerBO : scriptWorkerRoleBO.getScriptWorkerList()) {
                if (scriptWorkerBO.getWorkerId().equals(workerId) && scriptWorkerBO.getSelected()) {
                    return scriptWorkerRoleBO;
                }
            }
        }
        return null;
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

    public boolean hasTime(JbsOrderBO jbsOrderBO, ScriptWorkerBO scriptWorkerBO) {
        WorkerTime workerTime = workerTimeService.getByWorkerId(scriptWorkerBO.getWorkerId());
        if (workerTime == null) {
            return true;
        }

        List<Integer> daysList = Lists.newArrayList();
        Date orderDate = jbsOrderBO.getBeginTime();
        while (orderDate.compareTo(jbsOrderBO.getEndTime()) < 0) {
            daysList.add(DateUtil.getYmd(orderDate));
            orderDate = DateUtil.addDays(orderDate, 1);
        }
        /**
         * 0 日期
         * 1 周
         * 2 时间
         */
        String[] workerTimeArr = workerTime.getTime().split(",");
        String dateStr = workerTimeArr[0];
        String weekStr = workerTimeArr[1];
        String timeStr = workerTimeArr[2];

        //日期
        if (!"anytime".equals(dateStr)) {
            String[] workerTimeDateArr = dateStr.split("/");
            for (int i=0; i< workerTimeDateArr.length; i++) {
                String extDate = null;
                String date = workerTimeDateArr[i];
                if (workerTimeDateArr[i].contains("[")) {
                    extDate = workerTimeDateArr[i].substring(workerTimeDateArr[i].indexOf("[")+1, workerTimeDateArr[i].length()-1);
                    date = workerTimeDateArr[i].substring(0, workerTimeDateArr[i].indexOf("["));
                }

                if (extDate != null) {
                    String[] extDateArr = extDate.split("-");
                    if (DateUtil.isDateOverlapping(DateUtil.getEarliestOfDay(DateUtil.stringDate(extDateArr[0], "yyyyMMdd")),
                            DateUtil.getLatestOfDay(DateUtil.stringDate(extDateArr[1], "yyyyMMdd")),
                            jbsOrderBO.getBeginTime(),
                            jbsOrderBO.getEndTime()
                    )) {
                        return false;
                    }
                }

                if (date != null && date.length() > 0) {
                    String[] dateArr = date.split("-");
                    Integer begin = Integer.parseInt(dateArr[0]);
                    Integer end = Integer.parseInt(dateArr[1]);
                    Iterator<Integer> iterator = daysList.iterator();
                    while (iterator.hasNext()) {
                        Integer day = iterator.next();
                        if (begin >= day && day >= end) {
                            iterator.remove();
                        }
                    }
                    if (daysList.size() == 0) {
                        break;
                    }
                }
            }
        }

        //周
        if (!"anytime".equals(weekStr)) {
            if (weekStr.contains("[")) {
                for (Integer day : daysList) {
                    //包含 不上班的星期
                    if (weekStr.contains(DateUtil.getDayOfWeek(DateUtil.stringDate(day.toString(), "yyyyMMdd")).toString())) {
                        return false;
                    }
                }
            }
        }

        //时间
        if (!"anytime".equals(timeStr)) {
            String[] timeArr = timeStr.split("-");
            if (DateUtil.diffDays(jbsOrderBO.getBeginTime(), jbsOrderBO.getEndTime()) >= 1) {
                if (!"00:00-23:59".equals(weekStr)) {
                    return false;
                }
            } else {
                Date begin = DateUtil.stringDateToHHMM(timeArr[0]);
                Date end = DateUtil.stringDateToHHMM(timeArr[1]);
                Date orderBegin = DateUtil.format(jbsOrderBO.getBeginTime(), "HH:mm");
                Date orderEnd = DateUtil.format(jbsOrderBO.getEndTime(), "HH:mm");
                if (!(begin.compareTo(orderBegin) <= 0 && end.compareTo(orderEnd) >= 0)) {
                    return false;
                }
            }
        }

        return true;
    }

}
