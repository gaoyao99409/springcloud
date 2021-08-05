package com.springcloud.jbsdemo.service.order.impl;

import javax.annotation.Resource;

import com.springcloud.jbsdemo.mapper.JbsOrderWorkerMapper;
import com.springcloud.jbsdemo.service.order.JbsOrderWorkerService;
import org.springframework.stereotype.Service;

/**
 * @ClassName JbsOrderWorkerServiceImpl
 * @Description JbsOrderWorkerServiceImpl
 * @Author gaoyao
 * @Date 2021/8/5 4:24 PM
 * @Version 1.0
 */
@Service
public class JbsOrderWorkerServiceImpl implements JbsOrderWorkerService {

    @Resource
    JbsOrderWorkerMapper jbsOrderWorkerMapper;


}
