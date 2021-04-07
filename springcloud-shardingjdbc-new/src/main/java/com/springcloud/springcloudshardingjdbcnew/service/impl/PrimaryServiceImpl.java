package com.springcloud.springcloudshardingjdbcnew.service.impl;

import javax.annotation.Resource;

import com.springcloud.springcloudshardingjdbcnew.mapper.primary.PrimaryUserMapper;
import com.springcloud.springcloudshardingjdbcnew.model.primary.PrimaryUser;
import com.springcloud.springcloudshardingjdbcnew.service.PrimaryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ClassName PrimaryServiceImpl
 * @Description PrimaryServiceImpl
 * @Author gaoyao
 * @Date 2021/4/1 5:28 PM
 * @Version 1.0
 */
@Service
public class PrimaryServiceImpl implements PrimaryService {

    @Resource
    PrimaryUserMapper primaryUserMapper;

    @Override
    @Cacheable("targetClass + methodName + #id")
    public PrimaryUser selectByPrimaryKey(Long id) {
        return primaryUserMapper.selectByPrimaryKey(id);
    }
}
