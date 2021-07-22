package com.springcloud.jbsdemo.cal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.springcloud.jbsdemo.Enums.ThemeEnum;
import com.springcloud.jbsdemo.bean.Dm;
import com.springcloud.jbsdemo.bean.GraphMatch;
import com.springcloud.jbsdemo.bean.JbsDate;
import com.springcloud.jbsdemo.bean.Npc;
import com.springcloud.jbsdemo.bean.Order;
import com.springcloud.jbsdemo.bean.PriorityScript;
import com.springcloud.jbsdemo.bean.Room;
import com.springcloud.jbsdemo.bean.Script;
import com.springcloud.jbsdemo.util.DateUtil;

/**
 * @ClassName CalUtil
 * @Description CalUtil
 * @Author gaoyao
 * @Date 2021/7/22 4:24 PM
 * @Version 1.0
 */
public class CalUtil {

    public static void main(String[] args) {
        Script os = new Script(1l, 1, ThemeEnum.OUSHI.getCode());
        Script rs = new Script(2l, 1, ThemeEnum.RIBEN.getCode());
        Script kb = new Script(3l, 1, ThemeEnum.KONGBU.getCode());

        List<Integer> themeCodeList = Lists.newArrayList(ThemeEnum.RIBEN.getCode(),
                ThemeEnum.OUSHI.getCode(), ThemeEnum.KONGBU.getCode());

        /**
         * order
         */
        Order order = new Order(1l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), os, 5);
        Order order2 = new Order(2l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), rs, 6);
        Order order3 = new Order(3l, new JbsDate(new Date(), DateUtil.addDays(new Date(), 2)), kb, 7);
        List<Order> orderList  = Lists.newArrayList(order, order2, order3);

        /**
         * room
         */
        List<JbsDate> roomDateList = new ArrayList<>();
        roomDateList.add(new JbsDate(DateUtil.getEarliestOfDay(new Date()), DateUtil.addDays(new Date(), 3)));
        Room r1 = new Room(1l,10, themeCodeList, null, roomDateList);
        Room r2 = new Room(2l,5, themeCodeList, null, roomDateList);
        Room r3 = new Room(3l,6, themeCodeList, null, roomDateList);
        List<Room> roomList = Lists.newArrayList(r1, r2, r3);


        /**
         * dm
         */
        List<JbsDate> dateList = new ArrayList<>();
        dateList.add(new JbsDate(DateUtil.getEarliestOfDay(new Date()), DateUtil.addDays(new Date(), 3)));

        List<PriorityScript> dm1PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1),
                new PriorityScript(rs, 1),
                new PriorityScript(kb, 1));
        Dm dm1 = new Dm(1l, dateList, dm1PriorityScriptList);

        List<PriorityScript> dm2PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1));
        Dm dm2 = new Dm(2l, dateList, dm2PriorityScriptList);

        List<PriorityScript> dm3PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1),
                new PriorityScript(kb, 1));
        Dm dm3 = new Dm(3l, dateList, dm3PriorityScriptList);

        List<Dm> dmList = Lists.newArrayList(dm1, dm2, dm3);

        /**
         * npc
         */
        List<JbsDate> npcDateList = new ArrayList<>();
        npcDateList.add(new JbsDate(DateUtil.getEarliestOfDay(new Date()), DateUtil.addDays(new Date(), 3)));

        List<PriorityScript> npc1PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1),
                new PriorityScript(rs, 1), new PriorityScript(kb, 1));
        Npc npc1 = new Npc(1l, npcDateList, npc1PriorityScriptList);

        List<PriorityScript> npc2PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1));
        Npc npc2 = new Npc(2l, npcDateList, npc2PriorityScriptList);

        List<PriorityScript> npc3PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1),
                new PriorityScript(kb, 1));
        Npc npc3 = new Npc(3l, npcDateList, npc3PriorityScriptList);

        List<Npc> npcList = Lists.newArrayList(npc1, npc2, npc3);


        GraphMatch graphMatchDm = DmCal.doFind(orderList, dmList);
        GraphMatch graphMatchNpc = NpcCal.doFind(orderList, npcList);
        GraphMatch graphMatchRoom = RoomCal.doFind(orderList, roomList);

        for(int i = 0 ; i < graphMatchDm.getPath().length ; i ++){
            if (graphMatchDm.getPath()[i] >= 0
                    && graphMatchNpc.getPath()[i] >= 0
                    && graphMatchRoom.getPath()[i] >= 0) {
                System.out.println(graphMatchDm.getOrderArr()[i].getId() + " <---> (DM "
                        + graphMatchDm.getDmArr()[graphMatchDm.getPath()[i]].getId()
                        + ") <---> (NPC " + graphMatchNpc.getNpcArr()[graphMatchNpc.getPath()[i]].getId()
                        + ") <---> (ROOM " + graphMatchRoom.getRoomArr()[graphMatchRoom.getPath()[i]].getId()+")");
            }
        }
    }

}
