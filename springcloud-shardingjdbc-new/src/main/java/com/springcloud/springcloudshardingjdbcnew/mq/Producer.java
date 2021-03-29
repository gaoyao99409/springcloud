package com.springcloud.springcloudshardingjdbcnew.mq;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
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
@Slf4j
public class Producer implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, Object obj) {
        rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, obj, correlationData);
        log.info("消息发送成功:{}", obj);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        if (ack) {
            log.info("correlationData-->{},ack-->{},cause-->{}", correlationData, ack, cause);
        } else {
            log.info("correlationData-->{},ack-->{},cause-->{}", correlationData, ack, cause);
        }

    }

}