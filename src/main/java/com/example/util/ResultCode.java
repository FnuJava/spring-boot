package com.example.util;

public enum ResultCode {
	
	c_00000("成功"),
	c_00001("失败"),
	c_00002("用户未登录");
	
	String desc;

    private ResultCode(String desc)
    {
        this.desc = desc;
    }

    public String desc()
    {
        return desc;
    }

}
