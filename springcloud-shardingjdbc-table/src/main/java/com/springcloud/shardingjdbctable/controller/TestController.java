package com.springcloud.shardingjdbctable.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.springcloud.shardingjdbctable.mapper.OrderMapper;
import com.springcloud.shardingjdbctable.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TestController
 * @Author gaoyao
 * @Date 2021/4/20 2:03 PM
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    OrderMapper orderMapper;

    @PostMapping("/v1")
    public int save(){
        for (long i=1; i<=100;i++) {
            Order order = new Order();
            order.setCreateTime(new Date());
            order.setOrderId(i);
            order.setUserId(i);
            order.setId(i);
            orderMapper.insert(order);
        }

        return 1;
    }

}
