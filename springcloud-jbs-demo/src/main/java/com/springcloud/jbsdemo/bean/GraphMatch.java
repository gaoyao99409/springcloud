package com.springcloud.jbsdemo.bean;

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

    private Order[] orderArr;
    private Dm[] dmArr;
    private Npc[] npcArr;
    private Room[] roomArr;

}
