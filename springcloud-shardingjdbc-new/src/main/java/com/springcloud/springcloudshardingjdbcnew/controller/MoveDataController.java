package com.springcloud.springcloudshardingjdbcnew.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.springcloud.springcloudshardingjdbcnew.mapper.primary.PrimaryUserMapper;
import com.springcloud.springcloudshardingjdbcnew.mapper.secondary.SecondaryUserMapper;
import com.springcloud.springcloudshardingjdbcnew.model.primary.PrimaryUser;
import com.springcloud.springcloudshardingjdbcnew.model.secondary.SecondaryUser;
import com.springcloud.springcloudshardingjdbcnew.util.BeanTools;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MoveDataController
 * @Description MoveDataController
 * @Author gaoyao
 * @Date 2021/3/23 10:22 AM
 * @Version 1.0
 */
@RestController
@RequestMapping("/moveData")
public class MoveDataController {

    @Resource
    PrimaryUserMapper primaryUserMapper;
    @Resource
    SecondaryUserMapper secondaryUserMapper;

    @PostMapping("/v1")
    public String move(){

        List<PrimaryUser> primaryUserList = primaryUserMapper.getList(new HashMap<>());

        primaryUserList.forEach(primaryUser -> {
            if (newDbNotContains(primaryUser)) {
                secondaryUserMapper.insert(BeanTools.copyNonNullProperty(new SecondaryUser(), primaryUser));
            }
        });

        return "迁移完成";
    }

    private boolean newDbNotContains(PrimaryUser primaryUser) {
        SecondaryUser secondaryUser = secondaryUserMapper.selectByPrimaryKey(primaryUser.getId());
        if (secondaryUser == null) {
            return true;
        }
        return false;
    }

}
