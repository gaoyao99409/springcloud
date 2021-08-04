package com.springcloud.jbsdemo.controller;

import java.util.List;

import javax.annotation.Resource;

import com.springcloud.jbsdemo.mapper.JbsOrderMapper;
import com.springcloud.jbsdemo.model.JbsOrder;
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
    JbsOrderMapper orderMapper;

    @GetMapping("/order")
    public List<JbsOrder> getOrder(){
        List<JbsOrder> list = orderMapper.selectList(null);
        return list;
    }

    @GetMapping("/s")
    public String getS(){
        return "this is a test";
    }
}
