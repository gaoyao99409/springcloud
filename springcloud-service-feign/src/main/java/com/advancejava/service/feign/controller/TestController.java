package com.advancejava.service.feign.controller;

import com.advancejava.service.feign.client.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam String name) {
        return testService.sayHiFromClientOne( name );
    }

}
