package jbsdemo.bean;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName JbsDate
 * @Description JbsDate
 * @Author gaoyao
 * @Date 2021/7/20 11:54 AM
 * @Version 1.0
 */
@Data
public class JbsDate implements Comparable<JbsDate>{

    private Date start;
    private Date end;

    public JbsDate(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public JbsDate() {
    }

    @Override
    public int compareTo(JbsDate o) {
        return this.start.compareTo(o.getStart());
    }

//    public static void main(String[] args) {
//        List<JbsDate> list = new ArrayList();
//        JbsDate j = new JbsDate();
//        j.setStart(new Date());
//        list.add(j);
//
//        JbsDate j2 = new JbsDate();
//        j2.setStart(DateUtil.addDays(new Date(), -1));
//        list.add(j2);
//
//        Collections.sort(list);
//
//        System.out.println(list);
//    }
}
