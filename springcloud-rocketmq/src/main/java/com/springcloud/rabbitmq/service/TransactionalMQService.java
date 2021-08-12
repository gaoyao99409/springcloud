package com.springcloud.rabbitmq.service;

public interface TransactionalMQService {

    /**
     * 本地使用事务执行
     * @param msg
     * @return
     */
    boolean process(String msg);

    /**
     * 查询本地事务结果
     * @param msg
     * @return
     */
    boolean checkSuccess(String msg);

}
