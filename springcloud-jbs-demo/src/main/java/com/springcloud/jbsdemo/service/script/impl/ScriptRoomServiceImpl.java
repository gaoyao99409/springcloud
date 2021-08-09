package com.springcloud.jbsdemo.service.script.impl;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springcloud.jbsdemo.bean.bo.JbsOrderBO;
import com.springcloud.jbsdemo.bean.bo.ScriptRoomBO;
import com.springcloud.jbsdemo.mapper.ScriptRoomMapper;
import com.springcloud.jbsdemo.model.ScriptRoom;
import com.springcloud.jbsdemo.service.cal.RoomCal;
import com.springcloud.jbsdemo.service.order.OrderService;
import com.springcloud.jbsdemo.service.script.ScriptRoomService;
import com.springcloud.jbsdemo.util.BeanTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName ScriptRoomServiceImpl
 * @Description ScriptRoomServiceImpl
 * @Author gaoyao
 * @Date 2021/8/9 11:03 AM
 * @Version 1.0
 */
@Service
@Slf4j
public class ScriptRoomServiceImpl implements ScriptRoomService {

    @Resource
    ScriptRoomMapper scriptRoomMapper;
    @Resource
    OrderService orderService;
    @Resource
    RoomCal roomCal;

    @Override
    public void findAllOrderRoom(List<JbsOrderBO> orderBOList) {
        roomCal.find(orderBOList);
        for (JbsOrderBO bo : orderBOList) {
            for (ScriptRoomBO roomBO : bo.getScript().getScriptRoomList()) {
                if (roomBO.getSelected()) {
                    log.info("orderId:{}, roomId:{}", bo.getId(), roomBO.getRoomId());
                    break;
                }
            }
        }
    }

    @Override
    public List<ScriptRoomBO> getScriptRoomList(Long scriptId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("script_id", scriptId);
        List<ScriptRoom> scriptRoomList = scriptRoomMapper.selectList(queryWrapper);

        return BeanTools.copyParentList(ScriptRoom.class, ScriptRoomBO.class, scriptRoomList);
    }
}
