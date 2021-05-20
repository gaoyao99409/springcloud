package com.springcloud.shardingjdbctable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.springcloud.shardingjdbctable.mapper")
@SpringBootApplication
public class SpringcloudShardingjdbcTableApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudShardingjdbcTableApplication.class, args);
    }

}
