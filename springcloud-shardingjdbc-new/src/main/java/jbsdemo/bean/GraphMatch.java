package jbsdemo.bean;

import lombok.Data;

/**
 * @ClassName GraphMatch
 * @Description GraphMatch
 * @Author gaoyao
 * @Date 2021/7/20 5:18 PM
 * @Version 1.0
 */
@Data
public class GraphMatch {

    //所有的连接边
    private int[][] edges;
    //当前查找路径的某节点是否已经被扫描过
    private boolean[] checkedPath;
    //已找到路线
    private int[] path;
    private int[][] selectedPath;

    private Order[] orderArr;
    private Dm[] dmArr;
    private Npc[] npcArr;
    private Room[] roomArr;

    public boolean haveSelectedPath(int orderIndex){
        if (orderArr[orderIndex].getScript().getNpcCount() == 0) {
            return true;
        }
        for (int i=0; i<selectedPath[orderIndex].length; i++) {
            if (selectedPath[orderIndex][i] > -1) {
                return true;
            }
        }
        return false;
    }

    public boolean dmIsOk(int orderIndex){
        Order order = orderArr[orderIndex];
        if (order.getSelectedDmCount() >= order.getScript().getDmCount()) {
            return true;
        }
        return false;
    }

    public boolean npcIsOk(int orderIndex){
        Order order = orderArr[orderIndex];
        if (order.getSelectedNpcCount() >= order.getScript().getNpcCount()) {
            return true;
        }
        return false;
    }

    public int getOrderIndexByPath(int orderIndex){
        for (int i=0; i<path.length; i++) {
            if (orderIndex == path[i]) {
                return i;
            }
        }
        return -1;
    }

}
