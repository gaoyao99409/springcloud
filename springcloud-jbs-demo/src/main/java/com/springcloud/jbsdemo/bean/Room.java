package com.springcloud.jbsdemo.bean;

import java.util.List;

import lombok.Data;

/**
 * @ClassName Room
 * @Description Room
 * @Author gaoyao
 * @Date 2021/7/20 11:52 AM
 * @Version 1.0
 */
@Data
public class Room {

    private int pepleCount;
    private String theme;
    private List<PriorityScript> scriptList;
    private List<JbsDate> dateList;

}
