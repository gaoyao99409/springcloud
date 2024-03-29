package jbsdemo.bean;

import lombok.Data;

/**
 * @ClassName 剧本
 * @Description Script
 * @Author gaoyao
 * @Date 2021/7/20 11:56 AM
 * @Version 1.0
 */
@Data
public class Script {

    private Long id;

    private int time;
    //npc数量
    private int npcCount;
    //男npc数量
    private int boyNpcCount = -1;
    //女npc数量
    private int girlNpcCount = -1;

    //dm数量
    private int dmCount = 0;
    //男dm数量
    private int boyDmCount = -1;
    //女dm数量
    private int girlDmCount = -1;

    //DM反串NPC数量  直接减去需要的npc数量即可
//    private int dmToNpcCount;

    //主题
//    private String theme;
    private Integer themeCode;
    //人数
    private String pepleCount;

    public Script(Long id, int dmCount, int themeCode) {
        this.id = id;
        this.dmCount = dmCount;
        this.themeCode = themeCode;
    }

    public Script() {
    }
}
