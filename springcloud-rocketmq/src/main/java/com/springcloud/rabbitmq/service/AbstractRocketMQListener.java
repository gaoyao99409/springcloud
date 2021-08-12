package com.springcloud.rabbitmq.service;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @ClassName AbstractRocketMQListener
 * @Description AbstractRocketMQListener
 * @Author gaoyao
 * @Date 2021/8/12 5:17 PM
 * @Version 1.0
 */
@Component
public abstract class AbstractRocketMQListener implements TransactionListener, ApplicationContextAware {
    @Value("${rocketmq.producer.group}")
    private String producerGroupName;

    private static final String TOPIC = "TOPIC_A";

    private static ApplicationContext APPLICATION_CONTEXT;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 执行本地事务 并获取执行结果
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        String msg = new String(message.getBody());
        System.out.println("------------消息执行本地事务--------------");
        try {
            // 此处必须通过接口强制转换，否则很可能获取不到代理类
            boolean isCommit = ((TransactionalMQService) APPLICATION_CONTEXT.getBean(this.getClass())).process(msg);
            if (isCommit) {
                System.out.println("------------消息执行提交--------------");
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            System.out.println("------------消息执行回滚--------------");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------消息执行异常--------------");
        return LocalTransactionState.UNKNOW;
    }

    /**
     * 查询本地事务执行结果
     *
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("------------消息查询结果--------------");
        // 此处必须通过接口强制转换，否则很可能获取不到代理类
        boolean success = ((TransactionalMQService) APPLICATION_CONTEXT.getBean(this.getClass())).checkSuccess(new String(messageExt.getBody()));
        if (success) {
            System.out.println("------------查询消息提交--------------");
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        System.out.println("------------查询消息回滚--------------");
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

    /**
     * 发送事务消息
     *
     * @param tag 标签
     * @param msg 消息内容
     * @return
     */
    boolean sendTransactionalMsg(String tag, String msg) {
        String destination = StringUtils.isEmpty(tag) ? TOPIC : TOPIC + ":" + tag;
        // 此处如果使用MQ其他版本，可能导致强转异常
        ((TransactionMQProducer) rocketMQTemplate.getProducer()).setTransactionListener(this);
        org.springframework.messaging.Message message = MessageBuilder.withPayload(msg).build();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, null);
        System.out.println("Send transaction msg result: " + sendResult);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }

    /**
     * 发送同步消息
     *
     * @param tag 标签
     * @param msg 消息内容
     * @return
     */
    boolean sendSyncMsg(String tag, String msg) {
        String destination = StringUtils.isEmpty(tag) ? TOPIC : TOPIC + ":" + tag;
        org.springframework.messaging.Message<String> message = MessageBuilder.withPayload(msg).build();
        SendResult sendResult = rocketMQTemplate.syncSend(destination, message);
        System.out.println("Send syn msg result: " + sendResult);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }
}
