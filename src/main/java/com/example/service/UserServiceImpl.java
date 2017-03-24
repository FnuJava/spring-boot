package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
   
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public void add() {
		System.out.println("添加成功");
		jdbcTemplate.update("INSERT INTO score(user_id,value,startDay,endDay) values(1,0.00,now(),now())");
	}

}
