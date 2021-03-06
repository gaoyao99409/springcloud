package com.springcloud.springcloudshardingjdbcnew.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.google.common.collect.Maps;
import com.springcloud.springcloudshardingjdbcnew.mapper.primary.PrimaryUserMapper;
import com.springcloud.springcloudshardingjdbcnew.mapper.secondary.SecondaryUserMapper;
import com.springcloud.springcloudshardingjdbcnew.model.primary.PrimaryUser;
import com.springcloud.springcloudshardingjdbcnew.model.secondary.SecondaryUser;
import com.springcloud.springcloudshardingjdbcnew.util.BeanTools;
import com.springcloud.springcloudshardingjdbcnew.util.JsonUtil;
import com.springcloud.springcloudshardingjdbcnew.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
@Slf4j
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
                try {
                    secondaryUserMapper.insert(BeanTools.copyNonNullProperty(new SecondaryUser(), primaryUser));
                } catch (Exception e) {

                    e.printStackTrace();
                }
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

    @GetMapping("/v1/checkData")
    public int checkData(){

        int result = 1;

        HashMap<String, Object> map = Maps.newHashMap();
        long begin = 1;
        long limit = 1000;
        map.put("limit", 1000);
        map.put("begin", begin);
        List<PrimaryUser> primaryUserList = null;
        List<SecondaryUser> secondaryUserList = null;
        while ((primaryUserList = primaryUserMapper.getList(map)) != null &&
                primaryUserList.size() > 0 &&
                (secondaryUserList = secondaryUserMapper.getList(map)) != null &&
                secondaryUserList.size() > 0) {
            String md5Primary = MD5.getMD5(JsonUtil.toJson(primaryUserList));
            String md5Secondary = MD5.getMD5(JsonUtil.toJson(secondaryUserList));

            if (!md5Primary.equals(md5Secondary)) {
                log.info("从{}开始 数据有差异", primaryUserList.get(0).getId());
                return 0;
            }
            log.info("{}, 两个库的md5值={}, md5Primary={}, md5Secondary={}", primaryUserList.get(0).getId(), md5Primary.equals(md5Secondary), md5Primary, md5Secondary);
            begin += limit;
            map.put("begin", begin);
        }

        //至少有个是空
        if ((primaryUserList != null && primaryUserList.size() > 0)
                || (secondaryUserList != null && secondaryUserList.size() > 0)) {
            log.info("从{}开始 数据有差异", primaryUserList.get(0).getId());
            result = 0;
        }

        return result;
    }

}
