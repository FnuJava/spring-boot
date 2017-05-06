package com.example.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.User;
import com.example.service.UserService;
import com.example.util.Result;
import com.example.util.ResultData;

@RestController
public class RestfulController {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Value("${logging.file}")String  hh;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/users/{username}",method = RequestMethod.GET,consumes="application/json")
	public Result checkUserValid(@PathVariable String username,@RequestParam String pwd){
		System.out.println("执行了get"+username+pwd);
		return new Result(new ResultData(userService.getUsers(1)));
	}
	
	@RequestMapping(value = "/updateUser/{username}",method = RequestMethod.PUT,consumes="application/json")
	public User updateUser(@PathVariable String username,@RequestParam String pwd){
		User user = new User();
		user.setUserId(10);
		user.setUserName(username);
		user.setBirth(new Date(System.currentTimeMillis()));
		user.setCreateTime(new java.util.Date());
		System.out.println(pwd);
		return user;
	}
	/**
		@consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
		@produces: 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；
		  当consumes为json的时候接收数据只能用@RequestBody,并且该注解只能使用一次（流的形式传递）
		@PathVariable将某个动态参数放到URL请求路径中
		@RequestParam指定了请求参数名称
		
	 * @param username
	 * @param pwd
	 * @param maps
	 * @return
	 */
	@RequestMapping(value = "/addUser/{username}",method = RequestMethod.POST,produces="application/json")
	public Object addUser(@PathVariable String username,@RequestParam String pwd, @RequestParam Map<String, String> maps){
		
		logger.info("info"+username+pwd+maps);
		logger.debug("debug");
		userService.add();
		Map<String,Object> map = new HashMap<>();
		map.put("userName","张三");
		//return JSONObject.toJSONString(new Result(200,null,new ResultData(ResultCode.c_00001.name(),ResultCode.c_00001.desc(),map)),SerializerFeature.WriteMapNullValue);
		return new Result(new ResultData(map));
	}
	
	/* 
	 * @param pwd
	 * @param maps
	 * @return
	 */
	@RequestMapping(value = "/addTestUser/{username}",method = RequestMethod.POST,consumes="application/json",produces="application/json")
	public Object addUser(@PathVariable String username,@RequestBody String pwd){
		
		logger.info("infoddddddddddddd"+username+pwd);
		userService.add();
		Map<String,Object> map = new HashMap<>();
		map.put("userName","张三");
		return new Result(new ResultData(map));
	}
	
	@RequestMapping(value = "/delUser/{username}",method = RequestMethod.DELETE,consumes="application/json")
	public String delUser(String username,String pwd){
		return "true";
	}
	
	
	
	
}
