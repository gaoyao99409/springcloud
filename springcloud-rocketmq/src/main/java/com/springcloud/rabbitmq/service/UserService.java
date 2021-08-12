package com.springcloud.rabbitmq.service;

import com.springcloud.rabbitmq.bean.UserDTO;

public interface UserService {

    UserDTO getByUsername(String username);

    // 创建消息并处理本地逻辑
    boolean sendCreateUserMsg(String msg);
}
