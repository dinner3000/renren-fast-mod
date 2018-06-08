package com.brh.channel.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final int SEC = 1000;
	public static final String DATE_FORMAT_TIME = "HHmmss";
	public static final String DATE_FORMAT_TIME_MASK = "^\\d{6}$";
	public static final String DATE_FORMAT_DATE = "yyyyMMdd";
	public static final String DATE_FORMAT_MONTH = "yyyyMM";
	public static final String DATE_FORMAT_DATE_MASK = "^\\d{8}$";

	/** 日期格式：yyyy-MM-dd */
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATE_FORMAT_MASK = "^\\d{4}-\\d{2}-\\d{2}$";
	/** 时间格式：HH:mm:ss */
	public final static String TIME_FORMAT = "HH:mm:ss";
	public final static String TIME_FORMAT_MASK = "^\\d{2}:\\d{2}:\\d{2}$";
	/** 时间格式： yyyyMMddHHmmss */
	public static final String TIMESTAMP_FORMAT_NO_SEPARATOR = "yyyyMMddHHmmss";
	public static final String TIMESTAMP_FORMAT_NO_SEPARATOR_MASK = "^\\d{14}$";
	/**
	 * 注册的日期掩码
	 */
	static Map<String, String> PATTERNS = new HashMap<String, String>();
	/**
	 * 注册日期掩码
	 */
	static {
		PATTERNS.put(DATE_FORMAT_DATE_MASK, DATE_FORMAT_DATE);
		PATTERNS.put(DATE_FORMAT_TIME_MASK, DATE_FORMAT_TIME);
		PATTERNS.put(DATE_FORMAT_MASK, DATE_FORMAT);
		PATTERNS.put(TIME_FORMAT_MASK, TIME_FORMAT);
		PATTERNS.put(DATE_FORMAT_DATE_MASK, DATE_FORMAT_DATE);
		PATTERNS.put(TIMESTAMP_FORMAT_NO_SEPARATOR_MASK,
				TIMESTAMP_FORMAT_NO_SEPARATOR);
	}

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	/**
	 * 得到当前日期yyyyMMdd
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat dateForamt = new SimpleDateFormat(DATE_FORMAT_DATE);
		return dateForamt.format(new Date());
	}

	/**
	 * 得到当前时间HHmmss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat timeForamt = new SimpleDateFormat(DATE_FORMAT_TIME);
		return timeForamt.format(new Date());
	}

	/**
	 * 得到当前日期和时间 "yyyy-MM-dd HH:mm:ss";
	 * 
	 * @return
	 */
	public static String getCurrentFullTime() {
		SimpleDateFormat timeForamt = new SimpleDateFormat(DATE_FORMAT + " "
				+ TIME_FORMAT);
		return timeForamt.format(new Date());
	}

	/**
	 * 获取时间戳，如果参数为null，获取当前系统时间戳，否则获取参数的时间戳 格式"yyyyMMddHHmmss";
	 */
	public static String getTimestamp() {
		return getTimestamp(null);
	}

	/**
	 * 获取时间戳，如果参数为null，获取当前系统时间戳，否则获取参数的时间戳 格式"yyyyMMddHHmmss";
	 * 
	 * @return Sting
	 */
	public static String getTimestamp(final Date date) {
		SimpleDateFormat timeForamt = new SimpleDateFormat(
				TIMESTAMP_FORMAT_NO_SEPARATOR);
		if (null == date) {
			return timeForamt.format(new Date());
		} else {
			return timeForamt.format(date);
		}

	}
	/**
	 * 时间格式转换
	 * @param srcTm   原时间
	 * @param srcPattern 原时间串格式
	 * @param destPattern 目标时间串格式
	 * @return
	 */
	public static String transferTimeStr(String srcTm, String srcPattern,String destPattern) {
		SimpleDateFormat lsdStrFormat = new SimpleDateFormat(srcPattern);

		try {
			return format(lsdStrFormat.parse(srcTm), destPattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取当月YYYYMM 月份
	 */
	public static String getCurrentMonth(){
		SimpleDateFormat dateForamt = new SimpleDateFormat(DATE_FORMAT_DATE);
		return dateForamt.format(new Date());
	}
	/**
	 * 获取前n个月月份
	 */
	public static String getPreMonth(int n){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -n);//得到前3个月
		Date  formNow3Month = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DATE);
		return sdf.format(formNow3Month);
	}
}
