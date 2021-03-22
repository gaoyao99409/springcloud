package com.springcloud.springcloudshardingjdbc.old.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import com.springcloud.springcloudshardingjdbc.old.mapper.UserMapper;
import com.springcloud.springcloudshardingjdbc.old.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TestController
 * @Author gaoyao
 * @Date 2021/3/22 9:18 AM
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    @GetMapping("/v1")
    public Object getData(){
        return userMapper.getList(new HashMap<>());
    }

    @PostMapping("/v1")
    public int save(@RequestBody User user){
        return userMapper.insert(user);
    }

}
