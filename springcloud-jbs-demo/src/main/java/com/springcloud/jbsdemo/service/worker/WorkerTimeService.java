package com.springcloud.jbsdemo.service.worker;

import com.springcloud.jbsdemo.model.WorkerTime;

public interface WorkerTimeService {
    WorkerTime getByWorkerId(Long workerId);
}
