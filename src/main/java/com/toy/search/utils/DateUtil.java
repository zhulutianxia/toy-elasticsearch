package com.toy.search.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author guoyc on 2015-07-24.
 * @author Yancy on 2016-05-04.
 */
public class DateUtil {
	public static final int  DAYS_ONE_YEAR          = 365;
	public static final long MILLIS_ONE_SECOND      = 1000;
	public static final long MILLIS_ONE_MINUTE      = MILLIS_ONE_SECOND * 60L;
	public static final long MILLIS_ONE_HOUR        = MILLIS_ONE_MINUTE * 60;
	public static final long MILLIS_NINE_HOURS      = MILLIS_ONE_HOUR * 9;
	public static final long MILLIS_ONE_DAY         = MILLIS_ONE_HOUR * 24;
	public static final long MILLIS_ONE_WEEK        = MILLIS_ONE_DAY * 7;
	public static final long MILLIS_ONE_MONTH       = MILLIS_ONE_DAY * 30;
	public static final long MILLIS_THREE_MONTHS    = MILLIS_ONE_MONTH * 3;

	public static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT_2_ENGLISH = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
     * 计算日期与当前时间所差的月份数
     */
    public static int getMonthAge(long time) {
        long temp = System.currentTimeMillis() - time;
        return (int) (temp / MILLIS_ONE_MONTH);
    }
	
	/**
	 * @author: 冯彧
	 * @Desc:指定时间几天后的时间
	 * @createTime:2018年4月27日
	 */
	public static Date getNextDay(Date date ,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +day);//
		date = calendar.getTime();
		return date;
	}

	/**
	 * 日期转字符串[ 格式：yyyy-MM-dd ]
	 * @param date 日期
	 * @return
	 */
	public static String toString(Date date){
		String currentDate="";
		SimpleDateFormat format1 = YEAR_MONTH_DAY_FORMAT_2_ENGLISH;
		currentDate = format1.format(date);
		return currentDate;
	}

	/**
	 * 日期转字符串[ 格式：yyyy-MM-dd HH:mm:ss ]
	 * @param date 日期
	 * @return
	 */
	public static String toStrings(Date date){
		String currentDate="";
		SimpleDateFormat format1 = YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMAT;
		currentDate = format1.format(date);
		return currentDate;
	}
	
	/**
	 * 日期转字符串
	 * @param date 日期
	 * @param simpleDateFormat 指定类型
	 * @return
	 */
	public static String toStrings(Date date,SimpleDateFormat simpleDateFormat){
		String currentDate="";
		currentDate = simpleDateFormat.format(date);
		return currentDate;
	}

	/**
	 * 得到当前系统日期,格式:yyyy-mm-dd
	 * 
	 * @return
	 */
	public static String getCurrentDate(){
		return getFormatDate("yyyy-MM-dd");
	}

	/**
	 * 返回特定格式的日期
	 * 格式如下:
	 * yyyy-mm-dd
	 * @param formatString
	 * @return
	 */
	public static String getFormatDate(String formatString){
		String currentDate="";
		SimpleDateFormat format1 = new SimpleDateFormat(formatString);
		currentDate = format1.format(new Date());
		return currentDate;
	}

	/**
	 * 时间字符串转时间 【 日期格式：yyyy-MM-dd 】
	 * @param data 时间字符串
	 * @return 返回时间类型
	 * @throws ParseException
	 */
	public static Date toData(String data){
		if(StringUtils.isEmpty(data)) {
			return null;	
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(data);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 时间字符串转时间
	 * @param data 时间字符串
	 * @param formatString 字符串格式
	 * @return 返回时间类型
	 * @throws ParseException
	 */
	public static Date toData(String data,String formatString) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		return format.parse(data);

	}
}