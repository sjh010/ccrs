package com.mobileleader.edoc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static final String DEFAULT_FORMAT = "yyyyMMdd";
	
	public static SimpleDateFormat timeFormat;
	
	/**
	 * 현재시간 String 타입으로 변환 (yyyyMMdd)
	 */
	public static String getCurrentDateString() {
		return getCurrentDateString(DEFAULT_FORMAT);
	}
	
	/**
	 * 현재시간 format에 따른 String 타입으로 변환
	 */
	public static String getCurrentDateString(String format) {
		timeFormat = new SimpleDateFormat(format);
		return timeFormat.format(new Date());
	}
	
	/**
	 * Date타입 String 타입으로 변환
	 */
	public static String toString(Date date, String format) {
		if(date == null) {
			return null;
		}
		if(format == null) {
			format = DEFAULT_FORMAT;
		}
		
		timeFormat = new SimpleDateFormat(format);
		return timeFormat.format(date);
	}
	
	/**
	 * String타입의 시간데이터를 Date타입으로 변환 
	 */
	public static Date toDate(String dateStr, String format) throws ParseException {
		if(dateStr == null) {
			return null;
		}
		if(format == null) {
			format = DEFAULT_FORMAT;
		}
		
		timeFormat = new SimpleDateFormat(format);
		return timeFormat.parse(dateStr);
	}
}
