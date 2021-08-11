package com.springcloud.jbsdemo.service.worker.impl;

import javax.annotation.Resource;

import com.springcloud.jbsdemo.mapper.WorkerMapper;
import com.springcloud.jbsdemo.model.Worker;
import com.springcloud.jbsdemo.service.worker.WorkerService;
import org.springframework.stereotype.Service;

/**
 * @ClassName WorkerServiceImpl
 * @Description WorkerServiceImpl
 * @Author gaoyao
 * @Date 2021/8/10 9:31 AM
 * @Version 1.0
 */
@Service
public class WorkerServiceImpl implements WorkerService {

    @Resource
    WorkerMapper workerMapper;

    @Override
    public Worker getById(Long id) {
        return workerMapper.selectById(id);
    }
}
