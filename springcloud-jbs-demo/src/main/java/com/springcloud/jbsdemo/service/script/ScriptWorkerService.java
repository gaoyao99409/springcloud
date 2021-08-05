package com.springcloud.jbsdemo.service.script;

import java.util.List;

import com.springcloud.jbsdemo.bean.bo.ScriptWorkerRoleBO;

public interface ScriptWorkerService {
    List<ScriptWorkerRoleBO> getScriptWorkerRoleList(Long id);
}
