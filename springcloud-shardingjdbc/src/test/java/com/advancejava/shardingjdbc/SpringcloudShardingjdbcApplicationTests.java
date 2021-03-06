package com.springcloud.shardingjdbc;

import javax.annotation.Resource;

import com.springcloud.shardingjdbc.bean.User;
import com.springcloud.shardingjdbc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringcloudShardingjdbcApplication.class)
class SpringcloudShardingjdbcApplicationTests {

	@Resource
	UserService userService;

	@Test
	void contextLoads() {
		User user = new User();
		user.setAge(2);
		user.setName("test");
		userService.insertUser(user);
	}

}
