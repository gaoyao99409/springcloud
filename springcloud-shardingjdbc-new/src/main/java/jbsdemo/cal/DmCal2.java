package jbsdemo.cal;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jbsdemo.Enums.SexEnum;
import jbsdemo.Enums.ThemeEnum;
import jbsdemo.bean.*;
import jbsdemo.util.DateUtil;
import jbsdemo.util.JbsUtil;

import java.util.*;

/**
 * @ClassName 匈牙利算法
 * @Description DmCal
 * @Author gaoyao
 * @Date 2021/7/20 2:26 PM
 * @Version 1.0
 */
public class DmCal2 {

    public static void main(String[] args) {

        Script os = new Script(1l, 1, ThemeEnum.OUSHI.getCode());
        os.setDmCount(1);
        Script rs = new Script(2l, 1, ThemeEnum.RIBEN.getCode());
        rs.setDmCount(1);
        Script kb = new Script(3l, 1, ThemeEnum.KONGBU.getCode());
        kb.setDmCount(1);

        List<JbsDate> dateList = new ArrayList<>();
        dateList.add(new JbsDate(new Date(), DateUtil.addDays(new Date(), 3)));

        List<PriorityScript> dm1PriorityScriptList = new ArrayList<PriorityScript>();
        dm1PriorityScriptList.add(new PriorityScript(os, 1));
        dm1PriorityScriptList.add(new PriorityScript(rs, 1));
        dm1PriorityScriptList.add(new PriorityScript(kb, 1));
        Dm dm1 = new Dm(1l, dateList, dm1PriorityScriptList);

        List<PriorityScript> dm2PriorityScriptList = new ArrayList<PriorityScript>();
        dm2PriorityScriptList.add(new PriorityScript(os, 1));
        Dm dm2 = new Dm(2l, dateList, dm2PriorityScriptList);

        List<PriorityScript> dm3PriorityScriptList = new ArrayList<PriorityScript>();
        dm3PriorityScriptList.add(new PriorityScript(os, 1));
        dm3PriorityScriptList.add(new PriorityScript(kb, 1));
        Dm dm3 = new Dm(3l, dateList, dm3PriorityScriptList);

        List<Dm> dmList = new ArrayList<>();
        dmList.add(dm1);
        dmList.add(dm2);
        dmList.add(dm3);

        Order order = new Order();
        order.setDate(new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)));
        order.setScript(os);
        order.setId(1l);

        Order order2 = new Order();
        order2.setDate(new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)));
        order2.setScript(rs);
        order2.setId(2l);

        Order order3 = new Order();
        order3.setDate(new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)));
        order3.setScript(kb);
        order3.setId(3l);

        List<Order> orderList  = new ArrayList<>();
        orderList.add(order);
        orderList.add(order2);
        orderList.add(order3);


        log(doFind(orderList, dmList));
    }

    /**
     * 初始化数据
     * @return
     */
    private static GraphMatch init(List<Order> orderlist, List<Dm> dmList){
        GraphMatch graphMatch = new GraphMatch();
        int[][] edges = new int[orderlist.size()][dmList.size()];
        graphMatch.setDmArr(dmList.toArray(new Dm[dmList.size()]));
        graphMatch.setOrderArr(orderlist.toArray(new Order[orderlist.size()]));

        for (int i=0; i<graphMatch.getOrderArr().length; i++) {
            for (int j=0; j<graphMatch.getDmArr().length; j++) {
                edges[i][j] = 0;
                Order order = graphMatch.getOrderArr()[i];
                Dm dm = graphMatch.getDmArr()[j];
                if (JbsUtil.containsDate(dm.getDateList(), order.getDate())
                        && JbsUtil.containsScript(dm.getScriptList(), order.getScript())) {
                    edges[i][j] = JbsUtil.getScriptPriority(dm.getScriptList(), order.getScript());
                }
            }
        }

        graphMatch.setEdges(edges);
        graphMatch.setCheckedPath(new boolean[orderlist.size()]);
        int[] pathAry = new int[orderlist.size()];
        for(int i = 0 ; i < pathAry.length ; i ++){
            pathAry[i] = -1;
        }
        graphMatch.setPath(pathAry);

        int[][] selectedPathArr = new int[orderlist.size()][dmList.size()];
        for(int i = 0 ; i < selectedPathArr.length ; i ++){
            for (int j=0; j<selectedPathArr[0].length; j++) {
                selectedPathArr[i][j] = -1;
            }
        }
        graphMatch.setSelectedPath(selectedPathArr);

        return graphMatch;
    }


    /**
     * 清空当前路径上遍历过的Y点
     * @param graphMatch
     */
    private static void clearOnPathSign(GraphMatch graphMatch){
        graphMatch.setCheckedPath(new boolean[graphMatch.getOrderArr().length]);
    }


    public static GraphMatch doFind(List<Order> orderlist, List<Dm> dmList){
        GraphMatch graphMatch = init(orderlist, dmList);
        for(int i = 0 ; i < graphMatch.getOrderArr().length ; i ++) {
            search(graphMatch, i);
            clearOnPathSign(graphMatch);
        }
        return graphMatch;
    }

    /**
     * 对于某左侧节点X的查找
     * @param graphMatch 图的对象
     * @param orderIndex X的索引位置
     * @return 是否成功
     */
    private static boolean search(GraphMatch graphMatch , Integer orderIndex){
        Order order = graphMatch.getOrderArr()[orderIndex];
        //按照订单的主题分数排序dm
        int[] arr = graphMatch.getEdges()[orderIndex];
        List<Dm> list = Lists.newArrayList();
        //保存dm序号，要对dm进行优先级排序
        for (int i=0; i<graphMatch.getDmArr().length; i++) {
            Dm dm = graphMatch.getDmArr()[i];
            dm.setIndex(i);
            dm.setScore(arr[i]);
            list.add(dm);
        }

        Collections.sort(list, new Comparator<Dm>() {
            @Override
            public int compare(Dm o1, Dm o2) {
                return o1.getScore().compareTo(o2.getScore());
            }
        });

        boolean result = false;
        int selectedDmCount = order.getSelectedDmCount();
        int selectedDmBoyCount = order.getSelectedDmBoyCount();
        int selectedDmGirlCount = order.getSelectedDmGirlCount();
        for(Dm dm : list){
            int dmIndex = dm.getIndex();
            //没有连线
            if (graphMatch.getEdges()[orderIndex][dmIndex] <= 0 ){
                continue;
            }
            //已选
            if (graphMatch.getSelectedPath()[orderIndex][dmIndex] > -1) {
                continue;
            }

            //还需要男dm，但是当前是女dm
            /**
             * 需要男但当前是女，需要女但当前是男。continue
             */
            if ((order.getScript().getBoyDmCount() != -1
                    && selectedDmBoyCount < order.getScript().getBoyDmCount()
                    && dm.getSex() != SexEnum.BOY.getCode())
                    &&
                    (order.getScript().getGirlDmCount() != -1
                    && selectedDmGirlCount < order.getScript().getGirlDmCount()
                    && dm.getSex() != SexEnum.GIRL.getCode())) {
                continue;
            }

            //递归查找 当前Y节点是否是未覆盖点 或 是否在增广路径上（让已匹配节点去试一下连其他节点）
            if(JbsUtil.hasTime(dm, graphMatch.getOrderArr()[orderIndex])){
                //设置找到路径sc
                graphMatch.getSelectedPath()[orderIndex][dmIndex] = orderIndex;
                dm.getUsedDateList().add(order.getDate());
            } else {
                //dm可接此单，但是已有订单B，让订单B去试试其它dm
                if (JbsUtil.containsDate(dm.getDateList(), order.getDate())) {
                    Order orderB = null;
                    int orderBIndex = -1;
                    for (int bi=0; bi<graphMatch.getSelectedPath()[0].length; bi++) {
                        if (graphMatch.getSelectedPath()[bi][dmIndex] >= 0) {
                            JbsDate date1 = graphMatch.getOrderArr()[bi].getDate();
                            if (DateUtil.isDateOverlapping(date1.getStart(), date1.getEnd(), order.getDate().getStart(), order.getDate().getEnd())) {
                                orderB = graphMatch.getOrderArr()[bi];
                                orderBIndex = bi;
                                break;
                            }
                        }
                    }
                    //标记，占用这个dm的order时间, 把这个B订单的selectedDmCount减一,然后让这个B订单去找新的dm
                    orderB.setSelectedDmCount(orderB.getSelectedDmCount()-1);
                    if (orderB.getScript().getBoyDmCount() > 0) {
                        if (dm.getSex() == SexEnum.BOY.getCode()) {
                            orderB.setSelectedDmBoyCount(orderB.getSelectedDmBoyCount() - 1);
                        } else {
                            orderB.setSelectedDmGirlCount(orderB.getSelectedDmGirlCount() - 1);
                        }
                    }

                    if (search(graphMatch, orderBIndex)) {
                        //B订单找到了路径，order可以使用此dm了
                        //设置找到路径
                        graphMatch.getSelectedPath()[orderIndex][dmIndex] = orderIndex;
                    } else {
                        //没找到，恢复订单B
                        orderB.setSelectedDmCount(orderB.getSelectedDmCount() + 1);
                        if (orderB.getScript().getBoyDmCount() > 0) {
                            if (dm.getSex() == SexEnum.BOY.getCode()) {
                                orderB.setSelectedDmBoyCount(orderB.getSelectedDmBoyCount() + 1);
                            } else {
                                orderB.setSelectedDmGirlCount(orderB.getSelectedDmGirlCount() + 1);
                            }
                        }
                    }
                }
            }

            selectedDmCount += 1;
            if (order.getScript().getBoyDmCount() > 0) {
                if (dm.getSex() == SexEnum.BOY.getCode()) {
                    selectedDmBoyCount += 1;
                } else {
                    selectedDmGirlCount += 1;
                }
            }
            if (selectedDmCount >= order.getScript().getDmCount()) {
                break;
            }
        }

        order.setSelectedDmCount(selectedDmCount);
        order.setSelectedDmBoyCount(selectedDmBoyCount);
        order.setSelectedDmGirlCount(selectedDmGirlCount);

        /**
         * 查找失败，恢复订单其它dm路径
         * 查找成功，返回true
         */
        if (selectedDmCount >= order.getScript().getDmCount()) {
            return true;
        } else {
            for (int i=0; i<graphMatch.getSelectedPath()[orderIndex].length; i++) {
                if (graphMatch.getSelectedPath()[orderIndex][i] > -1) {
                    graphMatch.getDmArr()[i].getUsedDateList().remove(order.getDate());
                }
                graphMatch.getSelectedPath()[orderIndex][i] = -1;
            }
            return false;
        }
    }


    /**
     * 输出日志
     * @param graphMatch
     */
    private static void log(GraphMatch graphMatch){
        for(int i = 0 ; i < graphMatch.getSelectedPath().length ; i ++){
            System.out.print("order "+graphMatch.getOrderArr()[i].getId() + "<--->");
            for (int j=0; j< graphMatch.getSelectedPath()[i].length; j++) {
                if (graphMatch.getSelectedPath()[i][j] >= 0) {
                    System.out.print(graphMatch.getDmArr()[j]+",");
                }
            }
            System.out.println();
        }
    }

}
