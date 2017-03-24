package com.example.util;

import java.io.Serializable;

public class ResultData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String msg;
	private Object info;
	
	
	
	public ResultData(String code, String msg, Object info) {
		this.code = code;
		this.msg = msg;
		this.info = info;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getInfo() {
		return info;
	}
	public void setInfo(Object info) {
		this.info = info;
	}

}
