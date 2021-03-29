package com.springcloud.springcloudshardingjdbcnew.mq;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.springcloud.springcloudshardingjdbcnew.config.RabbitConfig;
import com.springcloud.springcloudshardingjdbcnew.mapper.secondary.SecondaryUserMapper;
import com.springcloud.springcloudshardingjdbcnew.model.secondary.SecondaryUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MsgConsumer
 * @Description MsgConsumer
 * @Author gaoyao
 * @Date 2021/3/23 1:40 PM
 * @Version 1.0
 */
@Component
@Slf4j
public class Consumer {

    @Resource
    SecondaryUserMapper secondaryUserMapper;
    @RabbitListener(
            bindings =
                    {
                            @QueueBinding(value = @Queue(value = RabbitConfig.CANAL_QUEUE, durable = "true"),
                                    exchange = @Exchange(value = RabbitConfig.CANAL_EXCHANGE),
                                    key = RabbitConfig.CANAL_ROUTINGKEY)
                    })
    @RabbitHandler
    public void processDirectMsg(Message massage, Channel channel) throws Exception {
        String msg = new String(massage.getBody(), StandardCharsets.UTF_8);
        log.info("收到canal消息：{}", msg);
        JSONObject jsonObject = JSONObject.parseObject(msg);

        SecondaryUser secondaryUser = JSONObject.parseObject(jsonObject.getJSONArray("data").getString(0), SecondaryUser.class);
        secondaryUserMapper.replaceUser(secondaryUser);

        channel.basicAck(massage.getMessageProperties().getDeliveryTag(), false);
        //throw new Exception("");
    }


}