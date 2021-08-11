package com.springcloud.jbsdemo.service.worker.impl;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springcloud.jbsdemo.mapper.WorkerMapper;
import com.springcloud.jbsdemo.mapper.WorkerTimeMapper;
import com.springcloud.jbsdemo.model.WorkerTime;
import com.springcloud.jbsdemo.service.worker.WorkerService;
import com.springcloud.jbsdemo.service.worker.WorkerTimeService;
import org.springframework.stereotype.Service;

/**
 * @ClassName WorkerServiceImpl
 * @Description WorkerServiceImpl
 * @Author gaoyao
 * @Date 2021/8/10 9:31 AM
 * @Version 1.0
 */
@Service
public class WorkerTimeServiceImpl implements WorkerTimeService {

    @Resource
    WorkerTimeMapper workerTimeMapper;

    @Override
    public WorkerTime getByWorkerId(Long workerId) {
        return workerTimeMapper.selectOne(new QueryWrapper<WorkerTime>().eq("worker_id", workerId));
    }
}
