package com.springcloud.jbsdemo.service.script.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerRoleBO;
import com.springcloud.jbsdemo.mapper.ScriptWorkerMapper;
import com.springcloud.jbsdemo.model.ScriptWorker;
import com.springcloud.jbsdemo.service.script.ScriptWorkerService;
import com.springcloud.jbsdemo.util.BeanTools;
import org.springframework.stereotype.Service;

/**
 * @ClassName ScriptWorkerServiceImpl
 * @Description ScriptWorkerServiceImpl
 * @Author gaoyao
 * @Date 2021/8/5 5:10 PM
 * @Version 1.0
 */
@Service
public class ScriptWorkerServiceImpl implements ScriptWorkerService {

    @Resource
    ScriptWorkerMapper scriptWorkerMapper;

    @Override
    public List<ScriptWorkerRoleBO> getScriptWorkerRoleList(Long scriptId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("script_id", scriptId);
        queryWrapper.eq("is_delete", 0);
        List<ScriptWorker> scriptWorkerList = scriptWorkerMapper.selectList(queryWrapper);

        Map<String, List<ScriptWorker>> scriptWorkerMap = scriptWorkerList.stream().collect(Collectors.groupingBy(ScriptWorker::getRoleName));

        List<ScriptWorkerRoleBO> scriptWorkerRoleBOList = Lists.newArrayList();
        for (String key : scriptWorkerMap.keySet()) {
            ScriptWorkerRoleBO scriptWorkerRoleBO = new ScriptWorkerRoleBO();
            scriptWorkerRoleBO.setRole(key);
            scriptWorkerRoleBO.setScriptWorkerList(BeanTools.copyParentList(ScriptWorker.class, ScriptWorkerBO.class, scriptWorkerMap.get(key)));
            scriptWorkerRoleBOList.add(scriptWorkerRoleBO);
        }

        return scriptWorkerRoleBOList;
    }
}
