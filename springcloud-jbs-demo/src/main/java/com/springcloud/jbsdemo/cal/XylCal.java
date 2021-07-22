package com.springcloud.jbsdemo.cal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.springcloud.jbsdemo.bean.Dm;
import com.springcloud.jbsdemo.bean.GraphMatch;
import com.springcloud.jbsdemo.bean.JbsDate;
import com.springcloud.jbsdemo.bean.Order;
import com.springcloud.jbsdemo.bean.PriorityScript;
import com.springcloud.jbsdemo.bean.Script;
import com.springcloud.jbsdemo.util.DateUtil;
import com.springcloud.jbsdemo.util.JbsUtil;
import org.apache.commons.collections.ListUtils;

/**
 * @ClassName 匈牙利算法
 * @Description XylCal
 * @Author gaoyao
 * @Date 2021/7/20 2:26 PM
 * @Version 1.0
 */
public class XylCal {

    static int count = 3;

    public static void main(String[] args) {

        Script os = new Script(1l, 1, "欧剧本1");
        Script rs = new Script(2l, 1, "日式剧本2");
        Script kb = new Script(3l, 1, "恐怖剧本3");

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

        GraphMatch graphMatch = init(orderList, dmList);
        for(int i = 0 ; i < count ; i ++) {
            search(graphMatch, i);
            clearOnPathSign(graphMatch);
        }
        log(graphMatch);
    }

    /**
     * 初始化数据
     * @return
     */
    private static GraphMatch init(List<Order> orderlist, List<Dm> dmList){
        GraphMatch graphMatch = new GraphMatch();
        int[][] edges = new int[dmList.size()][orderlist.size()];
        graphMatch.setDmArr(dmList.toArray(new Dm[dmList.size()]));
        graphMatch.setOrderArr(orderlist.toArray(new Order[orderlist.size()]));

        for (int i=0; i<graphMatch.getDmArr().length; i++) {
            for (int j=0; j<graphMatch.getOrderArr().length; j++) {
                edges[i][j] = 0;
                Dm dm = graphMatch.getDmArr()[i];
                Order order = graphMatch.getOrderArr()[j];
                if (JbsUtil.containsDate(dm.getDateList(), order.getDate())
                        && JbsUtil.containsScript(dm.getScriptList(), order.getScript())) {
                    edges[i][j] = JbsUtil.getScriptPriority(dm.getScriptList(), order.getScript());
                }
            }
        }

        graphMatch.setEdges(edges);
        graphMatch.setCheckedPath(new boolean[count]);
        int[] pathAry = new int[count];
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
        graphMatch.setCheckedPath(new boolean[count]);
    }

    /**
     * 对于某左侧节点X的查找
     * @param graphMatch 图的对象
     * @param xIndex X的索引位置
     * @return 是否成功
     */
    private static boolean search(GraphMatch graphMatch , Integer xIndex){

        for(int yIndex = 0 ; yIndex < count ; yIndex ++){
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
                System.out.println(graphMatch.getDmArr()[graphMatch.getPath()[i]] + "<--->" + graphMatch.getOrderArr()[i]);
            }
        }
    }

}
