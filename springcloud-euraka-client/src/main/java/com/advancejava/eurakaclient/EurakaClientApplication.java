package com.advancejava.eurakaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurakaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurakaClientApplication.class, args);
    }

    @GetMapping(value = "/get")
    public String testGet(){

        return "test success!";
    }
}
