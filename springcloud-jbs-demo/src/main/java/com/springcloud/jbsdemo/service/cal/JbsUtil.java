//package com.springcloud.jbsdemo.service.cal;
//
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
///**
// * @ClassName JbsUtil
// * @Description JbsUtil
// * @Author gaoyao
// * @Date 2021/7/21 9:56 AM
// * @Version 1.0
// */
//public class JbsUtil {
//
//    public static boolean  containsDate(List<JbsDate> list, JbsDate jbsDate) {
//
//        //list排序
//        Collections.sort(list);
//
//        Date calDate = jbsDate.getStart();
//        for (JbsDate d : list) {
//
//            if (d.getEnd().compareTo(jbsDate.getStart()) <= 0) {
//                continue;
//            } else if (d.getStart().compareTo(jbsDate.getEnd()) >= 0) {
//                break;
//            } else if (d.getStart().compareTo(calDate) <= 0
//                    && d.getEnd().compareTo(calDate) >= 0) {
//                calDate = d.getEnd();
//            }
//        }
//
//        if (calDate.compareTo(jbsDate.getEnd()) >= 0) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /**
//     * 判断是否包含此脚本
//     * @param list
//     * @param script
//     * @return
//     */
//    public static boolean containsScript(List<PriorityScript> list, Script script){
//        for (PriorityScript s : list) {
//            if (s.getId().equals(script.getId())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static int getScriptPriority(List<PriorityScript> list, Script script){
//        for (PriorityScript s : list) {
//            if (s.getId().equals(script.getId())) {
//                return s.getPriority();
//            }
//        }
//        return 1;
//    }
//
//    /**
//     * 查看dm时间对订单可用
//     * @param worker
//     * @param order
//     * @return
//     */
//    public static boolean hasTime(Worker worker, Order order) {
//        if (containsDate(worker.getDateList(), order.getDate())) {
//            for (JbsDate used : worker.getUsedDateList()) {
//                if (DateUtil.isDateOverlapping(used.getStart(), used.getEnd(), order.getDate().getStart(), order.getDate().getEnd())) {
//                    return false;
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//}
