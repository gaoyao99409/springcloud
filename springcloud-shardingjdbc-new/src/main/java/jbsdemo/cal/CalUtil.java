package jbsdemo.cal;

import com.google.common.collect.Lists;
import jbsdemo.Enums.ThemeEnum;
import jbsdemo.bean.*;
import jbsdemo.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        os.setNpcCount(1);
        Script rs = new Script(2l, 1, ThemeEnum.RIBEN.getCode());
        rs.setNpcCount(1);
        Script kb = new Script(3l, 1, ThemeEnum.KONGBU.getCode());
        kb.setNpcCount(1);

        List<Integer> themeCodeList = Lists.newArrayList(ThemeEnum.RIBEN.getCode(),
                ThemeEnum.OUSHI.getCode(), ThemeEnum.KONGBU.getCode());

        Date baseDate = DateUtil.stringDate("2021-07-26 00:00:00");

        /**
         * order
         */
        Order order = new Order(1l, new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)), os, 5);
        Order order2 = new Order(2l, new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)), rs, 6);
        Order order3 = new Order(3l, new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)), kb, 7);
        List<Order> orderList  = Lists.newArrayList(order, order2, order3);

        /**
         * room
         */
        List<JbsDate> roomDateList = new ArrayList<>();
        roomDateList.add(new JbsDate(DateUtil.getEarliestOfDay(baseDate), DateUtil.addDays(baseDate, 3)));
        Room r1 = new Room(1l,10, themeCodeList, null, roomDateList);
        Room r2 = new Room(2l,5, themeCodeList, null, roomDateList);
        Room r3 = new Room(3l,6, themeCodeList, null, roomDateList);
        List<Room> roomList = Lists.newArrayList(r1, r2, r3);


        /**
         * dm
         */
        List<JbsDate> dateList = new ArrayList<>();
        dateList.add(new JbsDate(DateUtil.getEarliestOfDay(baseDate), DateUtil.addDays(baseDate, 3)));

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
        npcDateList.add(new JbsDate(DateUtil.getEarliestOfDay(baseDate), DateUtil.addDays(baseDate, 3)));

        List<PriorityScript> npc1PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1),
                new PriorityScript(rs, 1), new PriorityScript(kb, 1));
        Npc npc1 = new Npc(1l, npcDateList, npc1PriorityScriptList);

        List<PriorityScript> npc2PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1));
        Npc npc2 = new Npc(2l, npcDateList, npc2PriorityScriptList);

        List<PriorityScript> npc3PriorityScriptList = Lists.newArrayList(new PriorityScript(os, 1),
                new PriorityScript(kb, 1));
        Npc npc3 = new Npc(3l, npcDateList, npc3PriorityScriptList);

        List<Npc> npcList = Lists.newArrayList(npc1, npc2, npc3);


        GraphMatch graphMatchDm = DmCal2.doFind(orderList, dmList);
        GraphMatch graphMatchNpc = NpcCal2.doFind(orderList, npcList);
        GraphMatch graphMatchRoom = RoomCal2.doFind(orderList, roomList);

        for(int orderIndex = 0 ; orderIndex < orderList.size() ; orderIndex ++){
            if (graphMatchDm.haveSelectedPath(orderIndex)
                    && graphMatchNpc.haveSelectedPath(orderIndex)
                    && graphMatchRoom.getOrderIndexByPath(orderIndex) >= 0) {
                System.out.print("order "+graphMatchDm.getOrderArr()[orderIndex].getId());

                System.out.print(" <---> (");
                for (int dmi=0; dmi<graphMatchDm.getSelectedPath()[orderIndex].length; dmi++) {
                    if (graphMatchDm.getSelectedPath()[orderIndex][dmi] >= 0) {
                        System.out.print(",DM" + graphMatchDm.getDmArr()[dmi].getId());
                    }
                }
                System.out.print(")");

                System.out.print(" <---> (");
                for (int npci=0; npci<graphMatchNpc.getSelectedPath()[orderIndex].length; npci++) {
                    if (graphMatchNpc.getSelectedPath()[orderIndex][npci] >= 0) {
                        System.out.print(",NPC" + graphMatchNpc.getNpcArr()[npci].getId());
                    }
                }
                System.out.print(")");

                System.out.println(" <---> (ROOM" + graphMatchRoom.getRoomArr()[graphMatchRoom.getOrderIndexByPath(orderIndex)].getId()+")");
            }
        }
    }

}
