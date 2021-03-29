package com.springcloud.springcloudshardingjdbcnew.controller;

import javax.annotation.Resource;

import com.springcloud.springcloudshardingjdbcnew.config.RabbitConfig;
import com.springcloud.springcloudshardingjdbcnew.mq.Producer;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MqController
 * @Description MqController
 * @Author gaoyao
 * @Date 2021/3/29 3:20 PM
 * @Version 1.0
 */
@RestController
@RequestMapping("mq")
public class MqController {

    @Resource
    Producer producer;

    @PutMapping("/v1")
    public int putMsg(@RequestParam("msg") String msg){
        producer.send(RabbitConfig.TEST_DIRECT_EXCHANGE, RabbitConfig.DIRECT_ROUTINGKEY, msg);
        return 1;
    }
}
