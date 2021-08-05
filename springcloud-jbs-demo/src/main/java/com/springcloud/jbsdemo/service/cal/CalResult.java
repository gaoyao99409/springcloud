package com.springcloud.jbsdemo.service.cal;

import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.model.JbsOrder;
import com.springcloud.jbsdemo.model.Room;
import com.springcloud.jbsdemo.model.Worker;
import lombok.Data;
import org.springframework.core.annotation.Order;

/**
 * @ClassName CalResult
 * @Description CalResult
 * @Author gaoyao
 * @Date 2021/7/20 5:18 PM
 * @Version 1.0
 */
@Data
public class CalResult {

    //所有的连接边
    private int[][] edges;
    //当前查找路径的某节点是否已经被扫描过
    private boolean[] checkedPath;
    //已找到路线
    private int[] path;
    private int[][] selectedPath;

    private JbsOrderBO[] orderArr;
    private Worker[] dmArr;
    private Room[] roomArr;

    public boolean haveSelectedPath(int orderIndex){

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
