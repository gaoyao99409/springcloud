package com.springcloud.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.springcloud.shardingjdbc.mapper")
public class SpringcloudShardingjdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudShardingjdbcApplication.class, args);
	}

}
