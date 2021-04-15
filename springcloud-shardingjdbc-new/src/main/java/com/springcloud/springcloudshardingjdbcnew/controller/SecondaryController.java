package com.springcloud.springcloudshardingjdbcnew.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Maps;
import com.springcloud.springcloudshardingjdbcnew.mapper.secondary.SecondaryUserMapper;
import com.springcloud.springcloudshardingjdbcnew.model.primary.PrimaryUser;
import com.springcloud.springcloudshardingjdbcnew.model.secondary.SecondaryUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PrimaryController
 * @Description PrimaryController
 * @Author gaoyao
 * @Date 2021/3/22 4:50 PM
 * @Version 1.0
 */
@RestController
@RequestMapping("/secondary")
public class SecondaryController {

    @Resource
    SecondaryUserMapper secondaryUserMapper;

    @GetMapping("/v1")
    public Object get(){
        return secondaryUserMapper.getList(new HashMap<>());
    }

    @GetMapping("/v1/max")
    public Object getMaxOne(){
        Map<String, Object> map = Maps.newHashMap();
        map.put("limit", 1);
        map.put("begin", 1);
        List<SecondaryUser> secondaryUserList = secondaryUserMapper.getList(map);
        return secondaryUserList.get(0);
    }

    @PostMapping("/v1")
    public int save(@RequestBody SecondaryUser secondaryUser){
        return secondaryUserMapper.insertSelective(secondaryUser);
    }
    
}
