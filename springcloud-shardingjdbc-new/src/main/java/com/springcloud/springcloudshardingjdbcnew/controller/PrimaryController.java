package com.springcloud.springcloudshardingjdbcnew.controller;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import com.springcloud.springcloudshardingjdbcnew.mapper.primary.PrimaryUserMapper;
import com.springcloud.springcloudshardingjdbcnew.model.primary.PrimaryUser;
import com.springcloud.springcloudshardingjdbcnew.service.PrimaryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PrimaryController
 * @Description PrimaryController
 * @Author gaoyao
 * @Date 2021/3/22 4:50 PM
 * @Version 1.0
 */
@RestController
@RequestMapping("/primary")
public class PrimaryController {

    @Resource
    PrimaryUserMapper primaryUserMapper;
    @Resource
    PrimaryService primaryService;

    @GetMapping("/v1/list")
    @Cacheable("targetClass + methodName + #param")
    public Object getList(@RequestParam(value = "param", required = false) String param){
        return primaryUserMapper.getList(new HashMap<>());
    }

    @GetMapping("/v1")
    public Object getOne(@RequestParam(value = "id") Long id){
        return primaryService.selectByPrimaryKey(id);
    }

    @PostMapping("/v1/batch")
    public int batchAdd(){
        for (int i=100000; i<1000000; i++) {
            PrimaryUser primaryUser = new PrimaryUser();
            primaryUser.setId(Long.valueOf(i));
            primaryUser.setAge(i);
            primaryUser.setName("name"+i);
            primaryUser.setCreateTime(new Date());
            primaryUserMapper.replaceIntoUser(primaryUser);
        }
        return 1;
    }

}
