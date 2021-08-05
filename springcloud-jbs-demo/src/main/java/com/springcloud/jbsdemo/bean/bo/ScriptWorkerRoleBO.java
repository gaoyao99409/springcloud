package com.springcloud.jbsdemo.bean.bo;

import java.util.List;

import lombok.Data;

/**
 * @ClassName 脚本角色可选dm列表
 * @Description ScriptWorkerRoleBO
 * @Author gaoyao
 * @Date 2021/8/4 4:20 PM
 * @Version 1.0
 */
@Data
public class ScriptWorkerRoleBO {
    private List<ScriptWorkerBO> scriptWorkerList;
    private String role;

    public boolean selected(){
        for (ScriptWorkerBO scriptWorkerBO : scriptWorkerList) {
            if (scriptWorkerBO.getSelected())
                return true;
        }
        return false;
    }
}
