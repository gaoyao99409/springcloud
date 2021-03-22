package com.springcloud.springcloudshardingjdbc.old;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = "com.springcloud.springcloudshardingjdbc.old.mapper")
@SpringBootApplication(scanBasePackages = {"com.springcloud.springcloudshardingjdbc.old"})
public class SpringcloudShardingjdbcOldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudShardingjdbcOldApplication.class, args);
    }

}
