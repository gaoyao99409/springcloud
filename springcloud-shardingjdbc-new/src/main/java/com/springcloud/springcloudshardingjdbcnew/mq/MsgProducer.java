package com.springcloud.springcloudshardingjdbcnew.mq;

import com.springcloud.springcloudshardingjdbcnew.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MsgProducer
 * @Description MsgProducer
 * @Author gaoyao
 * @Date 2021/3/23 1:43 PM
 * @Version 1.0
 */
@Component
public class MsgProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send2FanoutTestQueue(String massage){
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_FANOUT_EXCHANGE,
                "", massage);
    }

    public void send2DirectTestQueue(String massage){
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_DIRECT_EXCHANGE,
                RabbitConfig.DIRECT_ROUTINGKEY, massage);
    }

    public void send2TopicTestAQueue(String massage){
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_TOPIC_EXCHANGE,
                "test.aaa", massage);
    }

    public void send2TopicTestBQueue(String massage){
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_TOPIC_EXCHANGE,
                "test.bbb", massage);
    }

}