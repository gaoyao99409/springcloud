package jbsdemo.bean;

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

    private Long id;
    private int pepleCount;
    private List<Integer> themeCodeList;
    private List<PriorityScript> scriptList;
    private List<JbsDate> dateList;

    public Room(Long id, int pepleCount, List<Integer> themeCodeList, List<PriorityScript> scriptList, List<JbsDate> dateList) {
        this.id = id;
        this.pepleCount = pepleCount;
        this.themeCodeList = themeCodeList;
        this.scriptList = scriptList;
        this.dateList = dateList;
    }
}
