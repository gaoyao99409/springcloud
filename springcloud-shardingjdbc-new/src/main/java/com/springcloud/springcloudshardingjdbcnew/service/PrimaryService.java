package com.springcloud.springcloudshardingjdbcnew.service;

import com.springcloud.springcloudshardingjdbcnew.model.primary.PrimaryUser;

public interface PrimaryService {
    PrimaryUser selectByPrimaryKey(Long id);
}
