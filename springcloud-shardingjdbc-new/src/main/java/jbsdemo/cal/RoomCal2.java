package jbsdemo.cal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import jbsdemo.Enums.ThemeEnum;
import jbsdemo.bean.GraphMatch;
import jbsdemo.bean.JbsDate;
import jbsdemo.bean.Order;
import jbsdemo.bean.Room;
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
public class RoomCal2 {
    public static void main(String[] args) {

        Script os = new Script(1l, 1, ThemeEnum.OUSHI.getCode());
        Script rs = new Script(2l, 1, ThemeEnum.RIBEN.getCode());
        Script kb = new Script(3l, 1, ThemeEnum.KONGBU.getCode());

        List<Integer> themeCodeList = Lists.newArrayList(ThemeEnum.RIBEN.getCode(),
                ThemeEnum.OUSHI.getCode(), ThemeEnum.KONGBU.getCode());

        List<JbsDate> roomDateList = new ArrayList<>();
        roomDateList.add(new JbsDate(new Date(), DateUtil.addDays(new Date(), 3)));
        Room r1 = new Room(1l,10, themeCodeList, null, roomDateList);
        Room r2 = new Room(2l,5, themeCodeList, null, roomDateList);
        Room r3 = new Room(3l,6, themeCodeList, null, roomDateList);
        List<Room> roomList = Lists.newArrayList(r1, r2, r3);

        Order order = new Order(1l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), os, 5);
        Order order2 = new Order(2l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), rs, 6);
        Order order3 = new Order(3l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), kb, 7);
        List<Order> orderList  = new ArrayList<>();
        orderList.add(order);
        orderList.add(order2);
        orderList.add(order3);


        log(doFind(orderList, roomList));
    }

    public static GraphMatch doFind(List<Order> orderlist, List<Room> roomList){
        GraphMatch graphMatch = init(orderlist, roomList);
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
    private static GraphMatch init(List<Order> orderlist, List<Room> roomList){
        GraphMatch graphMatch = new GraphMatch();
        int[][] edges = new int[roomList.size()][orderlist.size()];
        graphMatch.setRoomArr(roomList.toArray(new Room[roomList.size()]));
        graphMatch.setOrderArr(orderlist.toArray(new Order[orderlist.size()]));

        for (int i=0; i<graphMatch.getOrderArr().length; i++) {
            for (int j=0; j<graphMatch.getRoomArr().length; j++) {
                edges[i][j] = 0;
                Room room = graphMatch.getRoomArr()[j];
                Order order = graphMatch.getOrderArr()[i];
                if (JbsUtil.containsDate(room.getDateList(), order.getDate())
                        && room.getThemeCodeList().contains(order.getScript().getThemeCode())
                        && room.getPepleCount() >= order.getPlayerCount()) {
                    edges[i][j] = 1;
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
     * @param orderIndex X的索引位置
     * @return 是否成功
     */
    private static boolean search(GraphMatch graphMatch , Integer orderIndex){

        for(int roomIndex = 0 ; roomIndex < graphMatch.getEdges()[orderIndex].length ; roomIndex ++){
            //没有连线
            if(graphMatch.getEdges()[orderIndex][roomIndex] <= 0 ){
                continue;
            }
            //已经检测过该节点
            if(graphMatch.getCheckedPath()[roomIndex]){
                continue;
            }
            //设置该节点已经检测过
            graphMatch.getCheckedPath()[roomIndex] = true;
            //递归查找 当前Y节点是否是未覆盖点 或 是否在增广路径上（让已匹配节点去试一下连其他节点）
            if(graphMatch.getPath()[roomIndex] == -1 || search(graphMatch , graphMatch.getPath()[roomIndex])){
                //设置找到路径
                graphMatch.getPath()[roomIndex] = orderIndex;
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
                System.out.println(graphMatch.getOrderArr()[graphMatch.getPath()[i]].getId() + "<--->" + graphMatch.getRoomArr()[i].getId());
            }
        }
    }

}
