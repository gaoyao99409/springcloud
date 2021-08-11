package com.springcloud.jbsdemo.bean.bo;

import com.springcloud.jbsdemo.model.JbsOrder;
import lombok.Data;

/**
 * @ClassName JbsOrderBO
 * @Description JbsOrderBO
 * @Author gaoyao
 * @Date 2021/8/4 1:40 PM
 * @Version 1.0
 */
@Data
public class JbsOrderBO extends JbsOrder {

    //todo worker信息需要共享

    private ScriptBO script;

    public void selectWorker(ScriptWorkerBO scriptWorkerBO){

    }

    private Long bakRoomId;

}
