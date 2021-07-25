package jbsdemo.cal;

import com.google.common.collect.Lists;
import jbsdemo.Enums.ThemeEnum;
import jbsdemo.bean.*;
import jbsdemo.util.DateUtil;
import jbsdemo.util.JbsUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName 匈牙利算法
 * @Description DmCal
 * @Author gaoyao
 * @Date 2021/7/20 2:26 PM
 * @Version 1.0
 */
public class NpcCal {

    public static void main(String[] args) {

        Script os = new Script(1l, 1, ThemeEnum.OUSHI.getCode());
        Script rs = new Script(2l, 1, ThemeEnum.RIBEN.getCode());
        Script kb = new Script(3l, 1, ThemeEnum.KONGBU.getCode());

        List<Integer> themeCodeList = Lists.newArrayList(ThemeEnum.RIBEN.getCode(),
                ThemeEnum.OUSHI.getCode(), ThemeEnum.KONGBU.getCode());

        List<JbsDate> npcDateList = new ArrayList<>();
        npcDateList.add(new JbsDate(new Date(), DateUtil.addDays(new Date(), 3)));
        List<PriorityScript> dm1PriorityScriptList = new ArrayList<PriorityScript>();
        dm1PriorityScriptList.add(new PriorityScript(os, 1));
        dm1PriorityScriptList.add(new PriorityScript(rs, 1));
        dm1PriorityScriptList.add(new PriorityScript(kb, 1));
        Npc dm1 = new Npc(1l, npcDateList, dm1PriorityScriptList);

        List<PriorityScript> dm2PriorityScriptList = new ArrayList<PriorityScript>();
        dm2PriorityScriptList.add(new PriorityScript(os, 1));
        Npc dm2 = new Npc(2l, npcDateList, dm2PriorityScriptList);

        List<PriorityScript> dm3PriorityScriptList = new ArrayList<PriorityScript>();
        dm3PriorityScriptList.add(new PriorityScript(os, 1));
        dm3PriorityScriptList.add(new PriorityScript(kb, 1));
        Npc dm3 = new Npc(3l, npcDateList, dm3PriorityScriptList);

        List<Npc> npcList = new ArrayList<>();
        npcList.add(dm1);
        npcList.add(dm2);
        npcList.add(dm3);

        Order order = new Order(1l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), os, 5);
        Order order2 = new Order(2l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), rs, 6);
        Order order3 = new Order(3l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), kb, 7);
        List<Order> orderList  = new ArrayList<>();
        orderList.add(order);
        orderList.add(order2);
        orderList.add(order3);


        log(doFind(orderList, npcList));
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
     * 初始化数据
     * @return
     */
    private static GraphMatch init(List<Order> orderlist, List<Npc> npcList){
        GraphMatch graphMatch = new GraphMatch();
        int[][] edges = new int[npcList.size()][orderlist.size()];
        graphMatch.setNpcArr(npcList.toArray(new Npc[npcList.size()]));
        graphMatch.setOrderArr(orderlist.toArray(new Order[orderlist.size()]));

        for (int i=0; i<graphMatch.getNpcArr().length; i++) {
            for (int j=0; j<graphMatch.getOrderArr().length; j++) {
                edges[i][j] = 0;
                Npc npc = graphMatch.getNpcArr()[i];
                Order order = graphMatch.getOrderArr()[j];
                if (JbsUtil.containsDate(npc.getDateList(), order.getDate())
                        && JbsUtil.containsScript(npc.getScriptList(), order.getScript())) {
                    edges[i][j] = JbsUtil.getScriptPriority(npc.getScriptList(), order.getScript());
                }
            }
        }

        graphMatch.setEdges(edges);
        graphMatch.setCheckedPath(new boolean[graphMatch.getOrderArr().length]);
        int[] pathAry = new int[graphMatch.getOrderArr().length];
        for(int i = 0 ; i < pathAry.length ; i ++){
            pathAry[i] = -1;
        }
        graphMatch.setPath(pathAry);
        return graphMatch;
    }


    /**
     * 清空当前路径上遍历过的Y点
     * @param graphMatch
     */
    private static void clearOnPathSign(GraphMatch graphMatch){
        graphMatch.setCheckedPath(new boolean[graphMatch.getOrderArr().length]);
    }

    /**
     * 对于某左侧节点X的查找
     * @param graphMatch 图的对象
     * @param xIndex X的索引位置
     * @return 是否成功
     */
    private static boolean search(GraphMatch graphMatch , Integer xIndex){

        for(int yIndex = 0 ; yIndex < graphMatch.getOrderArr().length ; yIndex ++){
            //没有连线
            if(graphMatch.getEdges()[xIndex][yIndex] <= 0 ){
                continue;
            }
            //已经检测过该节点
            if(graphMatch.getCheckedPath()[yIndex]){
                continue;
            }
            //设置该节点已经检测过
            graphMatch.getCheckedPath()[yIndex] = true;
            //递归查找 当前Y节点是否是未覆盖点 或 是否在增广路径上（让已匹配节点去试一下连其他节点）
            if(graphMatch.getPath()[yIndex] == -1 || search(graphMatch , graphMatch.getPath()[yIndex])){
                //设置找到路径
                graphMatch.getPath()[yIndex] = xIndex;
                return true;
            }
        }
        return false;
    }


    /**
     * 输出日志
     * @param graphMatch
     */
    private static void log(GraphMatch graphMatch){
        for(int i = 0 ; i < graphMatch.getPath().length ; i ++){
            if (graphMatch.getPath()[i] >= 0) {
                System.out.println(graphMatch.getNpcArr()[graphMatch.getPath()[i]] + "<--->" + graphMatch.getOrderArr()[i]);
            }
        }
    }

}
