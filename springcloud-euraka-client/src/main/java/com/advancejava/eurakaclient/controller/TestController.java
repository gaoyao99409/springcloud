package com.advancejava.eurakaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
//@Configuration
public class TestController {

    @Value("${spring.application.name}")
    private String springApplicationName;

    @GetMapping(value = "/get")
    public String testGet(@RequestParam String name){

        return springApplicationName + name + "test success!";
    }

    public static void main(String[] args) {
        String result = "1";
        StringBuilder newResult = new StringBuilder();
        for(int i=0; i<128; i++){
            int br = 0;
            for(int j=result.length(); j>0; j--){
                char data = result.charAt(j-1);
                int r = Integer.parseInt(data+"") * 2 + br;
                br = r / 10;
                newResult.insert(0,(r%10));
            }
            if(br > 0){
                newResult.insert(0, br);
            }
            result = newResult.toString();
            System.out.println(result);
            newResult = new StringBuilder();
        }

        System.out.println(result);
    }
}
