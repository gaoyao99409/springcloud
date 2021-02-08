package com.springcloud.shardingjdbc.mapper;

import com.springcloud.shardingjdbc.bean.User;


public interface UserDAO {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}