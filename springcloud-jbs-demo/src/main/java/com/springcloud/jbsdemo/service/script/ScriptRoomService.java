package com.springcloud.jbsdemo.service.script;

import java.util.List;

import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptRoomBO;

public interface ScriptRoomService {
    void findAllOrderRoom(List<JbsOrderBO> orderBOList);

    List<ScriptRoomBO> getScriptRoomList(Long id);
}
