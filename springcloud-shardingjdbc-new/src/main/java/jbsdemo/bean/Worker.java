package jbsdemo.bean;

import java.util.List;

import com.google.common.collect.Lists;
import lombok.Data;

/**
 * @ClassName Worker
 * @Description Worker
 * @Author gaoyao
 * @Date 2021/7/20 12:07 PM
 * @Version 1.0
 */
@Data
public class Worker extends LinkOrder {

    //性别
    public int sex;
    //工作时间
    public List<JbsDate> dateList;
    //工作剧本
    public List<PriorityScript> scriptList;
    //已被占用时间列表
    public List<JbsDate> usedDateList = Lists.newArrayList();
}
