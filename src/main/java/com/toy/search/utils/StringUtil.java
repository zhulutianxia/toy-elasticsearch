package com.toy.search.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	/**
	 * 从 value 中移除 item的值
	 * @param value 原字符串
	 * @param item 需要移除的字符串
	 * @param separateChar 原字符串中的间隔符
	 * @return
	 */
	public static String removeString(String value, String item, String separateChar){

		if(StringUtils.isEmpty(value)){ return ""; }

		value = separateChar + value + separateChar;

		value = value.replace(separateChar + item + separateChar, separateChar);

		if(value.equals(separateChar)){
			return "";
		}

		return trimEnd(trimStart(value,separateChar),separateChar);
	}

	/**
	 * 截取字符串开始匹配的字符
	 * @param str 要截取的字符串
	 * @param item 要匹配的字符串
	 * @return
	 */
	private static String trimStart(String str, String item){

		if(str.startsWith(item)){
			return str.substring(item.length(), str.length() - item.length());
		}
		return str;

	}

	/**
	 * 截取字符串最后匹配的字符
	 * @param str 要截取的字符串
	 * @param item 要匹配的字符串
	 * @return
	 */
	private static String trimEnd(String str, String item){

		if(str.endsWith(item)){
			return str.substring(0, str.length() - item.length());
		}
		return str;

	}
}

