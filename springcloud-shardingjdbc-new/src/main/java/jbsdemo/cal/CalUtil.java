package jbsdemo.cal;

import com.google.common.collect.Lists;
import jbsdemo.Enums.SexEnum;
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
        os.setDmCount(0);
        os.setBoyDmCount(1);
        os.setGirlDmCount(1);
        Script rs = new Script(2l, 1, ThemeEnum.RIBEN.getCode());
        rs.setNpcCount(1);
        Script kb = new Script(3l, 1, ThemeEnum.KONGBU.getCode());
        kb.setNpcCount(1);

        Script s1 = new Script(4l, 2, ThemeEnum.t_1.getCode());
        s1.setBoyDmCount(1);
        s1.setGirlDmCount(1);

        Script s2 = new Script(5l, 2, ThemeEnum.t_1.getCode());
        s2.setBoyDmCount(2);
        s2.setGirlDmCount(0);

        Script s3 = new Script(6l, 1, ThemeEnum.t_2.getCode());
        Script s4 = new Script(7l, 3, ThemeEnum.t_3.getCode());
        Script s5 = new Script(8l, 1, ThemeEnum.t_4.getCode());

        List<Integer> themeCodeList = Lists.newArrayList(ThemeEnum.RIBEN.getCode(),
                ThemeEnum.OUSHI.getCode(), ThemeEnum.KONGBU.getCode());

        Date baseDate = DateUtil.stringDate("2021-07-26 00:00:00");

        /**
         * order
         */
        Order order = new Order(1l, new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)), s3, 5);
        Date orderDate2 = DateUtil.stringDate("2021-07-26 10:00:00");
        Order order2 = new Order(2l, new JbsDate(orderDate2, DateUtil.addHours(orderDate2, 5)), s1, 6);
        Order order3 = new Order(3l, new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)), s5, 7);
        Order order4 = new Order(4l, new JbsDate(baseDate, DateUtil.addDays(baseDate, 2)), s4, 7);

        Date orderDate5 = DateUtil.stringDate("2021-07-26 15:00:00");
        Order order5 = new Order(5l, new JbsDate(orderDate5, DateUtil.addHours(orderDate5, 5)), s1, 7);
        List<Order> orderList  = Lists.newArrayList(order, order2, order3, order4, order5);

        /**
         * room
         */
        List<JbsDate> roomDateList = new ArrayList<>();
        roomDateList.add(new JbsDate(DateUtil.getEarliestOfDay(baseDate), DateUtil.addDays(baseDate, 1000000000)));

        List<Integer> tc1List = Lists.newArrayList(ThemeEnum.t_1.getCode());
        Room r1 = new Room(1l,10, tc1List, roomDateList);

        List<Integer> tc2List = Lists.newArrayList(ThemeEnum.t_1.getCode(), ThemeEnum.t_2.getCode());
        Room r2 = new Room(2l,10, tc2List, roomDateList);

        List<Integer> tc3List = Lists.newArrayList(ThemeEnum.t_2.getCode());
        Room r3 = new Room(3l,10, tc3List, roomDateList);

        List<Integer> tc4List = Lists.newArrayList(ThemeEnum.t_3.getCode());
        Room r4 = new Room(4l,10, tc4List, roomDateList);

        List<Integer> tc5List = Lists.newArrayList(ThemeEnum.t_1.getCode(), ThemeEnum.t_3.getCode());
        Room r5 = new Room(5l,10, tc5List, roomDateList);

        List<Integer> tc6List = Lists.newArrayList(ThemeEnum.t_4.getCode());
        Room r6 = new Room(6l,10, tc6List, roomDateList);

        List<Room> roomList = Lists.newArrayList(r1, r2, r3, r4, r5, r6);


        /**
         * dm
         */
        List<JbsDate> dateList = new ArrayList<>();
        dateList.add(new JbsDate(DateUtil.getEarliestOfDay(baseDate), DateUtil.addDays(baseDate, 100000000)));

        List<PriorityScript> dm1PriorityScriptList = Lists.newArrayList(
                new PriorityScript(s2, 25),
                new PriorityScript(s3, 25),
                new PriorityScript(s4, 25),
                new PriorityScript(s5, 25));
        Dm dm1 = new Dm(1l, dateList, dm1PriorityScriptList);
        dm1.setSex(SexEnum.BOY.getCode());

        List<PriorityScript> dm2PriorityScriptList = Lists.newArrayList(
                new PriorityScript(s1, 50),
                new PriorityScript(s3, 50));
        Dm dm2 = new Dm(2l, dateList, dm2PriorityScriptList);
        dm2.setSex(SexEnum.BOY.getCode());

        List<PriorityScript> dm3PriorityScriptList = Lists.newArrayList(
                new PriorityScript(s2, 33),
                new PriorityScript(s3, 33),
                new PriorityScript(s4, 33));
        Dm dm3 = new Dm(3l, dateList, dm3PriorityScriptList);
        dm3.setSex(SexEnum.GIRL.getCode());

        List<PriorityScript> dm4PriorityScriptList = Lists.newArrayList(
                new PriorityScript(s1, 33),
                new PriorityScript(s3, 33),
                new PriorityScript(s4, 33));
        Dm dm4 = new Dm(4l, dateList, dm4PriorityScriptList);
        dm4.setSex(SexEnum.GIRL.getCode());

        List<Dm> dmList = Lists.newArrayList(dm1, dm2, dm3, dm4);

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
            if (graphMatchDm.dmIsOk(orderIndex)
                    && graphMatchNpc.npcIsOk(orderIndex)
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
