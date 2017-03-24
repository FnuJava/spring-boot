package com.example.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.UserService;
import com.example.util.Result;
import com.example.util.ResultCode;
import com.example.util.ResultData;

@RestController
public class RestfulController {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Value("${logging.file}")String  hh;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/users/{username}",method = RequestMethod.GET,consumes="application/json")
	public String checkUserValid(String username,String pwd){
		return "true";
	}
	
	@RequestMapping(value = "/updateUser/{username}",method = RequestMethod.PUT,consumes="application/json")
	public User updateUser(String username,String pwd){
		User user = new User();
		user.setUserId(10);
		user.setUserName("张三");
		user.setBirth(new Date(System.currentTimeMillis()));
		user.setCreateTime(new java.util.Date());
		return user;
	}
	
	@RequestMapping(value = "/addUser/{username}",method = RequestMethod.POST,consumes="application/json")
	public Object addUser(String username,String pwd){
		
		logger.info("info"+hh);
		logger.debug("debug");
		userService.add();
		Map<String,Object> map = new HashMap<>();
		map.put("userName","张三");
		//return JSONObject.toJSONString(new Result(200,null,new ResultData(ResultCode.c_00001.name(),ResultCode.c_00001.desc(),map)),SerializerFeature.WriteMapNullValue);
		return new Result(Result.OK,null,new ResultData(ResultCode.c_00000.name(),ResultCode.c_00000.desc(),map));
	}
	
	@RequestMapping(value = "/delUser/{username}",method = RequestMethod.DELETE,consumes="application/json")
	public String delUser(String username,String pwd){
		return "true";
	}
	
	
	
	
}
