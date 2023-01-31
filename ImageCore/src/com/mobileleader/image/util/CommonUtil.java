package com.mobileleader.image.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {
	
	public static final String SIMPLE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	
	/**
	 * 개발자 PC IP
	 * @return
	 */
	public static String getDevIP() {
		return "10.102.123.76";
		//return "172.17.4.174";		
	}

	/**
	 * 내 아이피가 getDevIP() 아이피가 동일할 경우 함수실행을 무시하기 위해.
	 * @return
	 */
	public static boolean skipFun() {
		if (NetworkUtil.getIp().equals(CommonUtil.getDevIP())) {
			return false;
		}
		return true;		
	}
	
	/**
	 * base64 인코딩 여부 확인
	 */
	public static boolean isBase64Encoding(String encStr){
		String regex = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
		if(encStr.matches(regex)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Date 객체를 패턴에 맞게 변경 후 스트링으로 출력.
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			if (pattern == null || "".equals(pattern)) {
				pattern = SIMPLE_PATTERN;
			}
			SimpleDateFormat fmt = new SimpleDateFormat(pattern);
			return fmt.format(date);
		}
	}
	
	/**
	 * 스트링 날짜와 패턴을 넘기면 Date가 리턴됨.
	 * @param dateStr
	 * @param pattern
	 * @param local
	 * @return
	 * @throws ParseException
	 */
	public static final Date parseStringToDate(String dateStr, String pattern, Locale local) { 
		if (dateStr == null || dateStr.trim().equals("")) {
			return null;
		} else if (pattern == null || "".equals(pattern)) {
			pattern = SIMPLE_PATTERN;
		}
		
		SimpleDateFormat fmt = new SimpleDateFormat(pattern, local);
		Date date = null;
		try {
			date = fmt.parse(dateStr);
		} catch (ParseException e) {
			date = null;
		}
		
		return date;
	}


}
