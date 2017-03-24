package com.example.util;

import java.io.Serializable;

public class Result implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int  OK = 200;//请求成功
	public static final int C_error = 444; //客户端请求错误（参数不合法、数据类型不匹配等） 客户端的问题
	public static final String C_errorMsg = "请求不合法。";
	public static final int S_error = 555; //服务器处理出错   服务端的问题
	public static final String S_errorMsg = "系统繁忙,请稍后再试!";

	
	
	private int ret;
	private String msg;
	private Object data;
	private long timeMills;
	
	
	public Result(int ret,String msg,Object data){
		this.ret = ret;
		this.msg = msg;
		this.data = data;
		this.timeMills = System.currentTimeMillis();
	}
	/**
	 * 获取客户端错误Result
	 * @param data
	 * @return
	 */
	public Result getError_C(Object data){
		return new Result(C_error, C_errorMsg,data);
	}
	
	/**
	 * 获取服务端错误Result
	 * @param data
	 * @return
	 */
	public Result getError_S(Object data){
		return new Result(S_error, S_errorMsg,data);
	}
	
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the timeMills
	 */
	public long getTimeMills() {
		return timeMills;
	}

	/**
	 * @param timeMills the timeMills to set
	 */
	public void setTimeMills(long timeMills) {
		this.timeMills = timeMills;
	}
	
}
