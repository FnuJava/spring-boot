package com.example.controller;

import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class User {
	
	@JSONField(serialize=false)
	private int userId;
	private String userName;
	@JSONField (format="yyyy-MM-dd")  
	private Date birth;
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	private java.util.Date createTime;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
}
