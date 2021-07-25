package jbsdemo.bean;

import java.util.List;

import lombok.Data;

/**
 * @ClassName 主持人
 * @Description Dm
 * @Author gaoyao
 * @Date 2021/7/20 11:51 AM
 * @Version 1.0
 */
@Data
public class Dm extends Worker {

    private Long id;
    private Integer index;
    private Integer score;

    public Dm(Long id, List<JbsDate> dateList, List<PriorityScript> scriptList){
        this.id = id;
        this.dateList = dateList;
        this.scriptList = scriptList;
    }

    public Dm() {
    }
}
