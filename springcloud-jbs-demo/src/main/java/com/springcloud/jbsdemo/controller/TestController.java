package com.springcloud.jbsdemo.controller;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.collect.Maps;
import com.springcloud.jbsdemo.mapper.OrderMapper;
import com.springcloud.jbsdemo.model.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TestController
 * @Author gaoyao
 * @Date 2021/8/3 11:32 AM
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    OrderMapper orderMapper;

    @GetMapping("/order")
    public List<Order> getOrder(){
        List<Order> list = orderMapper.getList(Maps.newHashMap());
        return list;
    }

    @GetMapping("/s")
    public String getS(){
        return "this is a test";
    }
}
