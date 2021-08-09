package com.springcloud.jbsdemo.service.order;

import java.util.List;

import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;

/**
 * @ClassName OrderService
 * @Description OrderService
 * @Author gaoyao
 * @Date 2021/8/5 4:22 PM
 * @Version 1.0
 */
public interface OrderService {
    List<JbsOrderBO> findAllOrder();

    void findAllOrderWorker(List<JbsOrderBO> orderBOList);
}
