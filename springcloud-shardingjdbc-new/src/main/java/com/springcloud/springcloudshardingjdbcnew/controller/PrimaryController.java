package com.springcloud.springcloudshardingjdbcnew.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import com.springcloud.springcloudshardingjdbcnew.mapper.primary.PrimaryUserMapper;
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
@RequestMapping("/primary")
public class PrimaryController {

    @Resource
    PrimaryUserMapper primaryUserMapper;

    @GetMapping("/v1")
    public Object get(){
        return primaryUserMapper.getList(new HashMap<>());
    }

}
