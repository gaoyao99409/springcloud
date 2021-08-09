package com.springcloud.jbsdemo.service.order.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerRoleBO;
import com.springcloud.jbsdemo.mapper.JbsOrderWorkerMapper;
import com.springcloud.jbsdemo.model.JbsOrderWorker;
import com.springcloud.jbsdemo.model.ScriptWorker;
import com.springcloud.jbsdemo.service.order.JbsOrderWorkerService;
import org.springframework.stereotype.Service;

/**
 * @ClassName JbsOrderWorkerServiceImpl
 * @Description JbsOrderWorkerServiceImpl
 * @Author gaoyao
 * @Date 2021/8/5 4:24 PM
 * @Version 1.0
 */
@Service
public class JbsOrderWorkerServiceImpl implements JbsOrderWorkerService {

    @Resource
    JbsOrderWorkerMapper jbsOrderWorkerMapper;


    @Override
    public void addSelectedWorker(JbsOrderBO orderBO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderBO.getId());
        List<JbsOrderWorker> jbsOrderWorkerList = jbsOrderWorkerMapper.selectList(queryWrapper);
        Map<String, Long> roleWorkerMap = jbsOrderWorkerList.stream().collect(Collectors.toMap(JbsOrderWorker::getRole, JbsOrderWorker::getId));
        for (ScriptWorkerRoleBO scriptWorkerRoleBO : orderBO.getScript().getScriptWorkerRoleList()) {
            Long selectedWorkerId = roleWorkerMap.get(scriptWorkerRoleBO.getRole());
            for (ScriptWorkerBO scriptWorkerBO : scriptWorkerRoleBO.getScriptWorkerList()) {
                if (scriptWorkerBO.getWorkerId().equals(selectedWorkerId)) {
                    scriptWorkerBO.setSelected(true);
                    break;
                }
            }
        }
    }
}
