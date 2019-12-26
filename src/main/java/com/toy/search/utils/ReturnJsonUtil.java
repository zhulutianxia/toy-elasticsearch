package com.toy.search.utils;


import com.toy.search._enum.ResultEnum;

/**
 * Controller返回结果
 */
public class ReturnJsonUtil {

	/** 状态码 */
	private int code;

	/** 枚举定义的消息 */
	private String msg;

	/** 返回数据 */
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public ReturnJsonUtil() {

	}

	public ReturnJsonUtil(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * 只返回成功,不带数据的
	 * @return ResponseWrapper
	 */
	public static ReturnJsonUtil success() {
		return new ReturnJsonUtil(ResultEnum.SUCCESS.code,ResultEnum.SUCCESS.msg,null);
	}

	/**
	 * 返回成功，带数据的
	 * @return ResponseWrapper
	 */
	public static ReturnJsonUtil success(Object data) {
		return new ReturnJsonUtil(ResultEnum.SUCCESS.code,ResultEnum.SUCCESS.msg, data);
	}

	/**
	 * 自定义成功消息，并且带数据的(自定义指的是从封装好的枚举里选择)
	 * @return ResponseWrapper
	 */
	public static ReturnJsonUtil success(ResultEnum status,Object data) {
		return new ReturnJsonUtil(status.code,status.msg,data);
	}

	/**
	 * 返回失败，错误消息自己定义(自定义指的是从封装好的枚举里选择)
	 * @return ResponseWrapper
	 */
	public static ReturnJsonUtil error(ResultEnum status) {
		return new ReturnJsonUtil(status.code,status.msg,null);
	}

	/**
	 * 返回失败，错误消息自己定义
	 * @return ResponseWrapper
	 */
	public static ReturnJsonUtil error(ResultEnum status,String msg) {
		return new ReturnJsonUtil(status.code,msg,null);
	}

	/**
	 * 返回失败，自定义消息、错误码、数据
	 * @return ResponseWrapper
	 */
	public static ReturnJsonUtil error(ResultEnum status,String msg,Object data) {
		return new ReturnJsonUtil(status.code,msg,data);
	}

	/**
	 * 返回失败，错误消息统一(服务器故障，请稍后重试)
	 * @return ResponseWrapper
	 */
	public static ReturnJsonUtil error() {
		return new ReturnJsonUtil(ResultEnum.SERVER_ERROR.code,ResultEnum.SERVER_ERROR.msg,null);
	}

	@Override
	public String toString() {
		return "ReturnJsonUtil [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
}