package com.springcloud.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import com.springcloud.rabbitmq.bean.UserDTO;
import com.springcloud.rabbitmq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName IndexController
 * @Description IndexController
 * @Author gaoyao
 * @Date 2021/8/12 5:21 PM
 * @Version 1.0
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    private String index(@RequestParam("name") String name) {
        UserDTO user = new UserDTO();
        user.setUsername(name);
        user.setType(0);
        boolean success = userService.sendCreateUserMsg(JSONObject.toJSONString(user));
        return String.valueOf(success);
    }

}
