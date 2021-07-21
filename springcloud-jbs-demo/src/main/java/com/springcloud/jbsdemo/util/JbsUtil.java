package com.springcloud.jbsdemo.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.springcloud.jbsdemo.bean.JbsDate;
import com.springcloud.jbsdemo.bean.PriorityScript;
import com.springcloud.jbsdemo.bean.Script;

/**
 * @ClassName JbsUtil
 * @Description JbsUtil
 * @Author gaoyao
 * @Date 2021/7/21 9:56 AM
 * @Version 1.0
 */
public class JbsUtil {

    public static boolean containsDate(List<JbsDate> list, JbsDate jbsDate) {

        //list排序
        Collections.sort(list);

        Date calDate = jbsDate.getStart();
        for (JbsDate d : list) {

            if (d.getEnd().compareTo(jbsDate.getStart()) <= 0) {
                continue;
            } else if (d.getStart().compareTo(jbsDate.getEnd()) >= 0) {
                break;
            } else if (d.getStart().compareTo(calDate) <= 0
                    && d.getEnd().compareTo(calDate) >= 0) {
                calDate = d.getEnd();
            }
        }

        if (calDate.compareTo(jbsDate.getEnd()) >= 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断是否包含此脚本
     * @param list
     * @param script
     * @return
     */
    public static boolean containsScript(List<PriorityScript> list, Script script){
        for (PriorityScript s : list) {
            if (s.getId().equals(script.getId())) {
                return true;
            }
        }
        return false;
    }

    public static int getScriptPriority(List<PriorityScript> list, Script script){
        for (PriorityScript s : list) {
            if (s.getId().equals(script.getId())) {
                return s.getPriority();
            }
        }
        return 1;
    }

}
