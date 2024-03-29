package com.springcloud.jbsdemo.controller;

import java.util.List;

import javax.annotation.Resource;

import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.mapper.JbsOrderMapper;
import com.springcloud.jbsdemo.model.JbsOrder;
import com.springcloud.jbsdemo.service.order.OrderService;
import com.springcloud.jbsdemo.service.script.ScriptRoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description TestController
 * @Author gaoyao
 * @Date 2021/8/3 11:32 AM
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    JbsOrderMapper orderMapper;
    @Resource
    OrderService orderService;
    @Resource
    ScriptRoomService scriptRoomService;

    @GetMapping("/order")
    public List<JbsOrder> getOrder(){
        List<JbsOrder> list = orderMapper.selectList(null);
        return list;
    }

    @GetMapping("/find")
    public String find(){
        List<JbsOrderBO> orderBOList = orderService.findAllOrder();
        orderService.findAllOrderWorker(orderBOList);
        scriptRoomService.findAllOrderRoom(orderBOList);
        return "this is a test";
    }

    @GetMapping("/check")
    public String checkNewOrder(@RequestParam("scriptId") Long scriptId,
                                @RequestParam("beginTime") String beginTime,
                                @RequestParam("endTime") String endTime){

        return "检查完毕";
    }
}
