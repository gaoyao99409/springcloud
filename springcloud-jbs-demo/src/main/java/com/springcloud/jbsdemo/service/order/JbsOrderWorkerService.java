package com.springcloud.jbsdemo.service.order;

import java.util.List;

import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptWorkerRoleBO;

public interface JbsOrderWorkerService {
    void addSelectedWorker(JbsOrderBO orderBO);
}
