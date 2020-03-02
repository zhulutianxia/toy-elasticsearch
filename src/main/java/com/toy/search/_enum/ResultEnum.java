package com.toy.search._enum;

public enum ResultEnum {
	
    SUCCESS(0,"成功"),
    TOKEN_ERROR(106,"您的账号在其他设备上登录过，请重新登录"),
    SERVER_ERROR(401,"系统繁忙，我们正在努力搬运玩具~");
    public int code;
	public String msg;
	
	ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}