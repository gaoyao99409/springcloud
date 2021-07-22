package com.springcloud.jbsdemo.bean;

import java.util.List;

import lombok.Data;


/**
 * @ClassName Order
 * @Description Order
 * @Author gaoyao
 * @Date 2021/7/20 11:52 AM
 * @Version 1.0
 */
@Data
public class Order {

    private Integer playerCount;
    private Long id;
    private JbsDate date;
    private Script script;
    //订单指定dm
    private List<Dm> dm;

    public Order(Long id, JbsDate date, Script script) {
        this.id = id;
        this.date = date;
        this.script = script;
    }

    public Order(Long id, JbsDate date, Script script, Integer playerCount) {
        this.id = id;
        this.date = date;
        this.script = script;
        this.playerCount = playerCount;
    }

    public Order() {
    }
}
