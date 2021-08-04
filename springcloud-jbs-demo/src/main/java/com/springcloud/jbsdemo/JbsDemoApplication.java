package com.springcloud.jbsdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.springcloud.jbsdemo.mapper")
@SpringBootApplication
public class JbsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JbsDemoApplication.class, args);
	}

}
