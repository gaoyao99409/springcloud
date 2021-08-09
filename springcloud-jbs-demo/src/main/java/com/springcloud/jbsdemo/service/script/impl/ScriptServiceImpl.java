package com.springcloud.jbsdemo.service.script.impl;

import javax.annotation.Resource;

import com.springcloud.jbsdemo.bean.bo.ScriptBO;
import com.springcloud.jbsdemo.mapper.ScriptMapper;
import com.springcloud.jbsdemo.model.Script;
import com.springcloud.jbsdemo.service.script.ScriptRoomService;
import com.springcloud.jbsdemo.service.script.ScriptService;
import com.springcloud.jbsdemo.service.script.ScriptWorkerService;
import com.springcloud.jbsdemo.util.BeanTools;
import org.springframework.stereotype.Service;

/**
 * @ClassName ScriptServiceImpl
 * @Description ScriptServiceImpl
 * @Author gaoyao
 * @Date 2021/8/5 5:08 PM
 * @Version 1.0
 */
@Service
public class ScriptServiceImpl implements ScriptService {

    @Resource
    ScriptMapper scriptMapper;
    @Resource
    ScriptWorkerService scriptWorkerService;
    @Resource
    ScriptRoomService scriptRoomService;

    @Override
    public ScriptBO getScriptBO(Long scriptId) {
        Script script = scriptMapper.selectById(scriptId);
        ScriptBO scriptBO = BeanTools.copyParentToChild(Script.class, ScriptBO.class, script);
        scriptBO.setScriptWorkerRoleList(scriptWorkerService.getScriptWorkerRoleList(scriptBO.getId()));
        scriptBO.setScriptRoomList(scriptRoomService.getScriptRoomList(script.getId()));
        return scriptBO;
    }
}
