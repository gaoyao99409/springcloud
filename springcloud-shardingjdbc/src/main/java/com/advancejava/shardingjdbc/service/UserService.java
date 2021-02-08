package com.advancejava.shardingjdbc.service;

import javax.annotation.Resource;

import com.advancejava.shardingjdbc.bean.User;
import com.advancejava.shardingjdbc.mapper.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    UserDAO userDAO;

    public int insertUser(User user){
        return userDAO.insertSelective(user);
    }

}
