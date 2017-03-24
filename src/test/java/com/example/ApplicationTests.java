package com.example;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.entity.Users;
import com.example.util.BaseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	
	@Resource
	BaseService baseService;

	@Test
	public void contextLoads() {
		Users users =new Users();
		users.setName("张三");
		users.setDate(new Timestamp(System.currentTimeMillis()));
		baseService.insert(users);
		
	}

}
