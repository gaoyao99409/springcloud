package com.springcloud.jbsdemo.bean.bo;

import java.util.List;

import com.springcloud.jbsdemo.model.Script;
import com.springcloud.jbsdemo.model.ScriptWorker;
import lombok.Data;

/**
 * @ClassName ScriptBO
 * @Description ScriptBO
 * @Author gaoyao
 * @Date 2021/8/4 1:54 PM
 * @Version 1.0
 */
@Data
public class ScriptBO extends Script {
    private List<ScriptRoomBO> scriptRoomList;
    //脚本角色列表
    private List<ScriptWorkerRoleBO> scriptWorkerRoleList;

}
