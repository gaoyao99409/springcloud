package com.springcloud.jbsdemo.bean.bo;

import java.util.List;

import com.google.common.collect.Lists;
import com.springcloud.jbsdemo.model.ScriptWorker;
import lombok.Data;

/**
 * @ClassName ScriptWorkerBO
 * @Description ScriptWorkerBO
 * @Author gaoyao
 * @Date 2021/8/4 4:22 PM
 * @Version 1.0
 */
@Data
public class ScriptWorkerBO extends ScriptWorker {
    private Boolean selected = false;
    private List<JbsOrderBO> hasSelectedOrderList = Lists.newArrayList();

    private boolean lock = false;
}
