package com.springcloud.shardingjdbc.service;

import javax.annotation.Resource;

import com.springcloud.shardingjdbc.bean.User;
import com.springcloud.shardingjdbc.mapper.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    UserDAO userDAO;

    public int insertUser(User user){
        return userDAO.insertSelective(user);
    }

}
