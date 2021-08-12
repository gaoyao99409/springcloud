package com.springcloud.rabbitmq.service;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.springcloud.rabbitmq.bean.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserServiceImpl
 * @Description UserServiceImpl
 * @Author gaoyao
 * @Date 2021/8/12 5:20 PM
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends AbstractRocketMQListener implements UserService, TransactionalMQService{
//    @Autowired
//    private UserMapper userMapper;

    @Override
    public UserDTO getByUsername(String username) {
        return null;
    }

    @Override
    public boolean sendCreateUserMsg(String msg) {
        return super.sendTransactionalMsg("create_user", msg);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean process(String msg) {
        UserDTO user = JSONObject.parseObject(msg, UserDTO.class);
//        String[] names = user.getUsername().split("_");
//        for (String username : names) {
//            user.setUsername(username);
//            user.setCreatedTime(new Date());
//            if (userMapper.add(user) != 1) {
//                throw new RuntimeException("Add user failed");
//            }
//        }
        return true;
    }

    @Override
    public boolean checkSuccess(String msg) {
        // 查询数据是否已成功
        UserDTO user = JSONObject.parseObject(msg, UserDTO.class);
        String[] names = user.getUsername().split("_");
        for (String username : names) {
//            if (userMapper.selectByUsername(username) == null) {
//                return false;
//            }
        }
        return true;
    }
}
