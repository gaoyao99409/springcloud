package com.advancejava.service.feign.client.impl;

import com.advancejava.service.feign.client.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String sayHiFromClientOne(String name) {
        return null;
    }
}
