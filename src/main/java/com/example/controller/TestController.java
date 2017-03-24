package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.service.UserService;
import com.example.util.SpringUtil;

@RestController
public class TestController{
	
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/")
    public String home() {
        return "Hello World!";
    }
	
	@RequestMapping("/helloLogin")
    public ModelAndView helloLogin(ModelAndView view) {
		view.addObject("hello","张三");
		view.setViewName("/login");;
        return view;
    }
	
	@RequestMapping("/login")
    public User login() {
		User user = new User();
		user.setUserId(1010);
		user.setUserName("呵呵哒");
		
        return user;
    }
	
	@RequestMapping("/testException")
	public String testException(){
		return 100/0 +"";
	}
	
	@RequestMapping("/testSpringUtil")
	public String testSpringUtil(){
		logger.info("爱情转移");
		UserService userService = SpringUtil.getApplicationContext().getBean(UserService.class);
		userService.add();
		return "success";
	}
	
	public  static void main(String[] args) throws Exception {
		
        SpringApplication.run(TestController.class, args);
    }

}
