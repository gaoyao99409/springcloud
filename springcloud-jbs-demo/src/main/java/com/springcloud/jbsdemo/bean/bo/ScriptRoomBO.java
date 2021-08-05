package com.springcloud.jbsdemo.bean.bo;

import com.springcloud.jbsdemo.model.Room;
import com.springcloud.jbsdemo.model.ScriptRoom;
import lombok.Data;

/**
 * @ClassName ScriptRoomBO
 * @Description ScriptRoomBO
 * @Author gaoyao
 * @Date 2021/8/4 2:57 PM
 * @Version 1.0
 */
@Data
public class ScriptRoomBO extends ScriptRoom {
    private Room room;
    private Boolean selected = false;
}
