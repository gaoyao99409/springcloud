package com.springcloud.jbsdemo.bean;

import lombok.Data;

/**
 * @ClassName PriorityScript
 * @Description PriorityScript
 * @Author gaoyao
 * @Date 2021/7/20 11:51 AM
 * @Version 1.0
 */
@Data
public class PriorityScript extends Script{

    private int priority;

    public PriorityScript(Long id, int dmCount, Integer theme, int priority) {
        super(id, dmCount, theme);
        this.priority = priority;
    }

    public PriorityScript(Script script, int priority){
        super(script.getId(), script.getDmCount(), script.getThemeCode());
        this.priority = priority;
    }

}
