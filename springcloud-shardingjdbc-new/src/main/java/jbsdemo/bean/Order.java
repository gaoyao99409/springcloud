package jbsdemo.bean;

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

    int selectedDmCount = 0;
    int selectedDmBoyCount = 0;
    int selectedDmGirlCount = 0;

    int selectedNpcCount = 0;
    int selectedNpcBoyCount = 0;
    int selectedNpcGirlCount = 0;

    //订单默认是可用的，当dm、npc、room某一个资源不足时，为false
    boolean orderIsOk = true;

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
