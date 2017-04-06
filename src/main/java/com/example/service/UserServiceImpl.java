package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.entity.Users;

@Service
public class UserServiceImpl implements UserService{
   
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public void add() {
		System.out.println("添加成功");
		jdbcTemplate.update("INSERT INTO score(user_id,value,startDay,endDay) values(1,0.00,now(),now())");
	}

	@Override
	public Users getUsers(int id) {
		return jdbcTemplate.queryForObject("select * from users where id=1",new Users.Mapper());
	}

}
