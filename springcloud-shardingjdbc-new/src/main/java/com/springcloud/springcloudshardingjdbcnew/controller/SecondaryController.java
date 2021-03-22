package com.springcloud.springcloudshardingjdbcnew.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import com.springcloud.springcloudshardingjdbcnew.mapper.secondary.SecondaryUserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PrimaryController
 * @Description PrimaryController
 * @Author gaoyao
 * @Date 2021/3/22 4:50 PM
 * @Version 1.0
 */
@RestController
@RequestMapping("/secondary")
public class SecondaryController {

    @Resource
    SecondaryUserMapper secondaryUserMapper;

    @GetMapping("/v1")
    public Object get(){
        return secondaryUserMapper.getList(new HashMap<>());
    }
    
}
