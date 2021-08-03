package jbsdemo.cal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import jbsdemo.Enums.SexEnum;
import jbsdemo.Enums.ThemeEnum;
import jbsdemo.bean.Dm;
import jbsdemo.bean.GraphMatch;
import jbsdemo.bean.JbsDate;
import jbsdemo.bean.Npc;
import jbsdemo.bean.Order;
import jbsdemo.bean.PriorityScript;
import jbsdemo.bean.Script;
import jbsdemo.util.DateUtil;
import jbsdemo.util.JbsUtil;

/**
 * @ClassName 匈牙利算法
 * @Description DmCal
 * @Author gaoyao
 * @Date 2021/7/20 2:26 PM
 * @Version 1.0
 */
public class NpcCal2 {

    public static void main(String[] args) {

        Script os = new Script(1l, 1, ThemeEnum.OUSHI.getCode());
        os.setNpcCount(2);
        os.setBoyNpcCount(1);
        os.setGirlNpcCount(1);

        Script rs = new Script(2l, 1, ThemeEnum.RIBEN.getCode());
        rs.setNpcCount(1);
        Script kb = new Script(3l, 1, ThemeEnum.KONGBU.getCode());
        kb.setNpcCount(1);

        Date baseDate = DateUtil.stringDate("2021-07-26 00:00:00");
        List<JbsDate> dateList = new ArrayList<>();
        dateList.add(new JbsDate(baseDate, DateUtil.addDays(baseDate, 3)));

        /**
         * npc
         */
        List<JbsDate> npcDateList = new ArrayList<>();
        npcDateList.add(new JbsDate(baseDate, DateUtil.addDays(baseDate, 3)));

        List<PriorityScript> npc1PriorityScriptList = Lists.newArrayList(
                new PriorityScript(os, 5),
                new PriorityScript(rs, 1),
                new PriorityScript(kb, 4));
        Npc npc1 = new Npc(1l, npcDateList, npc1PriorityScriptList);
        npc1.setSex(SexEnum.BOY.getCode());

        List<PriorityScript> npc2PriorityScriptList = Lists.newArrayList(
                new PriorityScript(os, 10));
        Npc npc2 = new Npc(2l, npcDateList, npc2PriorityScriptList);
        npc2.setSex(SexEnum.BOY.getCode());

        List<PriorityScript> npc3PriorityScriptList = Lists.newArrayList(
                new PriorityScript(os, 4),
                new PriorityScript(kb, 6));
        Npc npc3 = new Npc(3l, npcDateList, npc3PriorityScriptList);
        npc3.setSex(SexEnum.BOY.getCode());

        List<PriorityScript> npc4PriorityScriptList = Lists.newArrayList(
                new PriorityScript(os, 5),
                new PriorityScript(rs, 5));
        Npc npc4 = new Npc(4l, npcDateList, npc4PriorityScriptList);
        npc4.setSex(SexEnum.BOY.getCode());

        List<Npc> npcList = Lists.newArrayList(npc1, npc2, npc3, npc4);

        Order order = new Order();
        order.setDate(new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)));
        order.setScript(os);
        order.setId(1l);

        Order order2 = new Order();
        order2.setDate(new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)));
        order2.setScript(rs);
        order2.setId(2l);

        Order order3 = new Order();
        order3.setDate(new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)));
        order3.setScript(kb);
        order3.setId(3l);

        List<Order> orderList  = new ArrayList<>();
        orderList.add(order);
        orderList.add(order2);
        orderList.add(order3);


        log(doFind(orderList, npcList));
    }

    /**
     * 初始化数据
     * @return
     */
    private static GraphMatch init(List<Order> orderlist, List<Npc> npcList){
        GraphMatch graphMatch = new GraphMatch();
        int[][] edges = new int[orderlist.size()][npcList.size()];
        graphMatch.setNpcArr(npcList.toArray(new Npc[npcList.size()]));
        graphMatch.setOrderArr(orderlist.toArray(new Order[orderlist.size()]));

        for (int i=0; i<graphMatch.getOrderArr().length; i++) {
            for (int j=0; j<graphMatch.getNpcArr().length; j++) {
                edges[i][j] = 0;
                Order order = graphMatch.getOrderArr()[i];
                Npc npc = graphMatch.getNpcArr()[j];
                if (JbsUtil.containsDate(npc.getDateList(), order.getDate())
                        && JbsUtil.containsScript(npc.getScriptList(), order.getScript())) {
                    edges[i][j] = JbsUtil.getScriptPriority(npc.getScriptList(), order.getScript());
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

        int[][] selectedPathArr = new int[orderlist.size()][npcList.size()];
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


    public static GraphMatch doFind(List<Order> orderlist, List<Npc> npcList){
        GraphMatch graphMatch = init(orderlist, npcList);
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
        if (!order.isOrderIsOk()) {
            return false;
        }

        //按照订单的主题分数排序dm
        int[] arr = graphMatch.getEdges()[orderIndex];
        List<Npc> list = Lists.newArrayList();
        //保存dm序号，要对dm进行优先级排序
        for (int i=0; i<graphMatch.getNpcArr().length; i++) {
            Npc npc = graphMatch.getNpcArr()[i];
            npc.setIndex(i);
            npc.setScore(arr[i]);
            list.add(npc);
        }

        Collections.sort(list, new Comparator<Npc>() {
            @Override
            public int compare(Npc o1, Npc o2) {
                return o2.getScore().compareTo(o1.getScore());
            }
        });

        boolean result = false;
        int selectedNpcCount = order.getSelectedNpcCount();
        int selectedNpcBoyCount = order.getSelectedNpcBoyCount();
        int selectedNpcGirlCount = order.getSelectedNpcGirlCount();
        for(Npc npc : list){
            int npcIndex = npc.getIndex();
            //没有连线
            if (graphMatch.getEdges()[orderIndex][npcIndex] <= 0 ){
                continue;
            }
            //已选
            if (graphMatch.getSelectedPath()[orderIndex][npcIndex] > -1) {
                continue;
            }

            //还需要男dm，但是当前是女dm
            /**
             * 需要男但当前是女，需要女但当前是男。continue
             */
            if (npc.getSex() == SexEnum.GIRL.getCode()) {
                if (order.getScript().getGirlNpcCount() != -1
                        && selectedNpcGirlCount >= order.getScript().getGirlNpcCount()) {
                    //不再需要女dm
                    continue;
                }
            } else {
                if (order.getScript().getBoyNpcCount() != -1
                        && selectedNpcBoyCount >= order.getScript().getBoyNpcCount()) {
                    //不再需要男dm
                    continue;
                }
            }

            //递归查找 当前Y节点是否是未覆盖点 或 是否在增广路径上（让已匹配节点去试一下连其他节点）
            if(JbsUtil.hasTime(npc, graphMatch.getOrderArr()[orderIndex])){
                //设置找到路径sc
                graphMatch.getSelectedPath()[orderIndex][npcIndex] = orderIndex;
                npc.getUsedDateList().add(order.getDate());
            } else {
                //npc可接此单，但是已有订单B，让订单B去试试其它npc
                if (JbsUtil.containsDate(npc.getDateList(), order.getDate())) {
                    Order orderB = null;
                    int orderBIndex = -1;
                    for (int bi=0; bi<graphMatch.getSelectedPath()[0].length; bi++) {
                        if (graphMatch.getSelectedPath()[bi][npcIndex] >= 0) {
                            JbsDate date1 = graphMatch.getOrderArr()[bi].getDate();
                            if (DateUtil.isDateOverlapping(date1.getStart(), date1.getEnd(), order.getDate().getStart(), order.getDate().getEnd())) {
                                orderB = graphMatch.getOrderArr()[bi];
                                orderBIndex = bi;
                                break;
                            }
                        }
                    }
                    //标记，占用这个dm的order时间, 把这个B订单的selectedDmCount减一,然后让这个B订单去找新的dm
                    orderB.setSelectedNpcCount(orderB.getSelectedNpcCount()-1);
                    if (orderB.getScript().getBoyNpcCount() > 0) {
                        if (npc.getSex() == SexEnum.BOY.getCode()) {
                            orderB.setSelectedNpcBoyCount(orderB.getSelectedNpcBoyCount() - 1);
                        } else {
                            orderB.setSelectedNpcGirlCount(orderB.getSelectedNpcGirlCount() - 1);
                        }
                    }
                    graphMatch.getCheckedPath()[orderBIndex] = true;
                    if (search(graphMatch, orderBIndex)) {
                        //B订单找到了路径，order可以使用此dm了
                        //设置找到路径
                        graphMatch.getSelectedPath()[orderIndex][npcIndex] = orderIndex;
                        //去掉订单B和此dm的连接路径
                        graphMatch.getSelectedPath()[orderBIndex][npcIndex] = -1;
                        graphMatch.getCheckedPath()[orderBIndex] = false;
                    } else {
                        //没找到，恢复订单B
                        orderB.setSelectedNpcCount(orderB.getSelectedNpcCount() + 1);
                        if (orderB.getScript().getBoyNpcCount() > 0) {
                            if (npc.getSex() == SexEnum.BOY.getCode()) {
                                orderB.setSelectedNpcBoyCount(orderB.getSelectedNpcBoyCount() + 1);
                            } else {
                                orderB.setSelectedNpcGirlCount(orderB.getSelectedNpcGirlCount() + 1);
                            }
                        }
                        graphMatch.getCheckedPath()[orderBIndex] = false;
                        continue;
                    }
                }
            }

            selectedNpcCount += 1;
            if (order.getScript().getBoyNpcCount() > 0) {
                if (npc.getSex() == SexEnum.BOY.getCode()) {
                    selectedNpcBoyCount += 1;
                } else {
                    selectedNpcGirlCount += 1;
                }
            }
            if (selectedNpcCount >= order.getScript().getNpcCount()) {
                break;
            }
        }

        order.setSelectedNpcCount(selectedNpcCount);
        order.setSelectedNpcBoyCount(selectedNpcBoyCount);
        order.setSelectedNpcGirlCount(selectedNpcGirlCount);

        /**
         * 查找失败，恢复订单其它dm路径
         * 查找成功，返回true
         */
        if (selectedNpcCount >= order.getScript().getNpcCount()) {
            return true;
        } else {
            //B订单去找其他路径没找到情况下，不重置B订单已找到的路径
            if (!graphMatch.getCheckedPath()[orderIndex]) {
                for (int i=0; i<graphMatch.getSelectedPath()[orderIndex].length; i++) {
                    if (graphMatch.getSelectedPath()[orderIndex][i] > -1) {
                        graphMatch.getNpcArr()[i].getUsedDateList().remove(order.getDate());
                    }
                    graphMatch.getSelectedPath()[orderIndex][i] = -1;
                }
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
            System.out.print("order "+graphMatch.getOrderArr()[i].getId() + "<---> ");
            for (int j=0; j< graphMatch.getSelectedPath()[i].length; j++) {
                if (graphMatch.getSelectedPath()[i][j] >= 0) {
                    System.out.print("npc"+graphMatch.getNpcArr()[j].getId()+",");
                }
            }
            System.out.println();
        }
    }

}
