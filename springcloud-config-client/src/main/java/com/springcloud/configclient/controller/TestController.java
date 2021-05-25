package com.springcloud.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TestController
 * @Author gaoyao
 * @Date 2021/5/25 9:59 AM
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${foo}")
    String config;

    @GetMapping("/config")
    public String config(){
        return config;
    }

}
