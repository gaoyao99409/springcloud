package com.springcloud.jbsdemo.service.order.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptRoomBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerRoleBO;
import com.springcloud.jbsdemo.mapper.JbsOrderMapper;
import com.springcloud.jbsdemo.model.JbsOrder;
import com.springcloud.jbsdemo.model.Worker;
import com.springcloud.jbsdemo.service.cal.WorkerDfsCal;
import com.springcloud.jbsdemo.service.order.JbsOrderWorkerService;
import com.springcloud.jbsdemo.service.order.OrderService;
import com.springcloud.jbsdemo.service.script.ScriptService;
import com.springcloud.jbsdemo.service.worker.WorkerService;
import com.springcloud.jbsdemo.util.BeanTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderServiceImpl
 * @Description OrderServiceImpl
 * @Author gaoyao
 * @Date 2021/8/5 4:22 PM
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    JbsOrderMapper jbsOrderMapper;
    @Resource
    ScriptService scriptService;
    @Resource
    WorkerDfsCal workerCal;
    @Resource
    JbsOrderWorkerService jbsOrderWorkerService;
    @Resource
    WorkerService workerService;


    @Override
    public List<JbsOrderBO> findAllOrder(){
        List<JbsOrder> orderList = jbsOrderMapper.selectList(new QueryWrapper<JbsOrder>().eq("is_delete", 0));
        List<JbsOrderBO> orderBOList = BeanTools.copyParentList(JbsOrder.class, JbsOrderBO.class, orderList);
        for (JbsOrderBO orderBO : orderBOList) {
            orderBO.setScript(scriptService.getScriptBO(orderBO.getScriptId()));
            jbsOrderWorkerService.addSelectedWorker(orderBO);
            addSelectedRoom(orderBO);
        }
        return orderBOList;
    }

    private void addSelectedRoom(JbsOrderBO orderBO) {
        for (ScriptRoomBO scriptRoomBO : orderBO.getScript().getScriptRoomList()) {
            if (scriptRoomBO.getRoomId().equals(orderBO.getRoomId())) {
                scriptRoomBO.setSelected(true);
                break;
            }
        }
    }

    @Override
    public void findAllOrderWorker(List<JbsOrderBO> orderBOList){
        workerCal.find(orderBOList);
        for (JbsOrderBO bo : orderBOList) {
            for (ScriptWorkerRoleBO worker : bo.getScript().getScriptWorkerRoleList()) {
                if (worker.selected()) {
                    Worker worker1 = workerService.getById(worker.getSelectedId());
                    log.info("orderId:{}, role:{}, workerId:{}", bo.getId(), worker.getRole(), worker1.getName());
//                    getSelectedIdΩbreak;
                }
            }
        }
    }

}
