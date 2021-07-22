package com.springcloud.jbsdemo.bean;

import java.util.List;

import lombok.Data;

/**
 * @ClassName Npc
 * @Description Npc
 * @Author gaoyao
 * @Date 2021/7/20 11:51 AM
 * @Version 1.0
 */
@Data
public class Npc extends Worker {

    private Long id;

    public Npc(Long id, List<JbsDate> dateList, List<PriorityScript> scriptList){
        this.id = id;
        this.dateList = dateList;
        this.scriptList = scriptList;
    }

    public Npc() {
    }

}
