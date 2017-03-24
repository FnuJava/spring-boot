package com.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注解方式自定义servlet 注意在Application.java 添加注解    @ServletComponentScan ！！！！
 * 一般很少用 会导致sping mvc 的dispatchservlet不会被调用
 * @author Administrator
 *
 */
@WebServlet( urlPatterns="/image",description="Servlet的说明") 
public class MyServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.err.println("get");
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.err.println("post");
		
	}
	
}
