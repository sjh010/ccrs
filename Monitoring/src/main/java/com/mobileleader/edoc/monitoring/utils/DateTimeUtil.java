package com.mobileleader.edoc.monitoring.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeUtil {

	private static String dateDelim = "-";
	private static final int millisPerHour = 60 * 60 * 1000;

	/**
	 * YYYY-MM-DD HH:MI:SS 형태의 날짜 문자열을 Timestamp Type으로 변환
	 *
	 * @param	strDate	날짜문자열 (YYYY-MM-DD HH:MI:SS)
	 * @return	Timestamp
	 */
	public static Timestamp toTimestamp(String strDate) throws NumberFormatException {
		Timestamp tDate = null;
		if (strDate != null && strDate.length() == 19) {
			Calendar cDate = getDateTime(strDate);
			tDate = new Timestamp(cDate.getTimeInMillis());
		}
		return tDate;
	}

	/**
	 * String type의 date를 Calendar type으로 변환해준다.
	 *
	 * @param strDate 변환할 date
	 * @return Calendar type object
	 * @exception java.lang.NumberFormatException
	 */
	public static Calendar getDateTime(String strDate) throws NumberFormatException {
		int year, month, day, hour, min, sec;

		strDate = Tools.replaceStr(strDate, "-", "");
		strDate = Tools.replaceStr(strDate, ".", "");
		strDate = Tools.replaceStr(strDate, ":", "");
		strDate = Tools.replaceStr(strDate, "T", "");
		strDate = Tools.replaceStr(strDate, "Z", "");
		
		year = Integer.parseInt(strDate.substring(0, 4));
		month = Integer.parseInt(strDate.substring(4, 6))-1;
		day = Integer.parseInt(strDate.substring(6, 8));
		hour = Integer.parseInt(strDate.substring(8, 10));
		min = Integer.parseInt(strDate.substring(10, 12));
		sec = Integer.parseInt(strDate.substring(12, 14));

		Calendar cal = Calendar.getInstance(SimpleTimeZone.getDefault());
		cal.set(year, month, day, hour, min, sec);
		return cal;
	}

	/**
	 * Calendar type을 String type으로 변환시킨다.
	 *
	 * @param cal Calendar type date
	 * @return String 으로 변환된 date ("yyyy-mm-dd")
	 */
	public static String getDateString(Calendar cal){
		
        String DATE_FORMAT = "yyyy" + dateDelim + "MM" + dateDelim + "dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleTimeZone timeZone = new SimpleTimeZone( 9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);
		Date idate = cal.getTime();
        return sdf.format(idate);
	}
	
	/**
	 * 날짜문자열을 여러가지 형태로 변환
	 * 
	 * @param cal 로컬 기준 Calendar 
	 * @return 변환된 형태의 날짜
	 */
	public static String setDateFormat(Calendar cal, String dateType) {
		
		SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, "KST");

		Date idate = cal.getTime();
		
		String DATE_FORMAT = null;
		if (dateType.equals("HanMD")) {
			DATE_FORMAT = "M월 d일";
		} else if (dateType.equals("HanYMD")) {
			DATE_FORMAT = "yyyy년 M월 d일";
		} else if (dateType.equals("MD")) {
			DATE_FORMAT = "yyyy.MM.dd";
		} else if (dateType.equals("HanHM")) {
			DATE_FORMAT = "a h:mm";
		} else if (dateType.equals("Hm")) {
			DATE_FORMAT = "HH:mm";
		} else if (dateType.equals("hm")) {
			DATE_FORMAT = "h:mm";
		} else if (dateType.equals("HanMDHM")) {
			DATE_FORMAT = "M월 d일 a h:mm";
		} else if (dateType.equals("HanYMDHM")) {
			DATE_FORMAT = "yyyy년 M월 d일 a h:mm";
		} else if (dateType.equals("HanYMDdHM")) {
			DATE_FORMAT = "yyyy년 M월 d일 E요일 a h:mm";
		} else if (dateType.equals("MDHanHM")) {
			DATE_FORMAT = "yyyy.MM.dd a h:mm";
		} else if (dateType.equals("yyyyMMdd")) {
			DATE_FORMAT = "yyyyMMdd";
		} else if (dateType.equals("HHmm")) {
			DATE_FORMAT = "HHmm";
		} else if (dateType.equals("yyyyMMddHHmm")) {
			DATE_FORMAT = "yyyyMMddHHmm";
		} else if (dateType.equals("yyyyMMddHHmmss")) {
			DATE_FORMAT = "yyyyMMddHHmmss";
		} else {
			DATE_FORMAT = "M월 d일 a h:mm";
		}
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.KOREAN);
        sdf.setTimeZone(timeZone);
        
		return sdf.format(idate);
	}
	
	/**
	 * 날짜문자열을 여러가지 형태로 변환
	 * 
	 * @param strDate GMT 기준 날짜문자열 (ex: 20140424T065600Z) 
	 * @return 변환된 형태의 날짜
	 */
	public static String setDateFormat(String strDate, String dateType) {
		if (strDate == null || strDate.equals("")) {
			return "";	
		}
		if (dateType == null) {
			dateType = "HanMDHM";
		}
		
		int year, month, day, hour, min, sec;

		strDate = Tools.replaceStr(strDate, "T", "");
		strDate = Tools.replaceStr(strDate, "Z", "");
		
		year = Integer.parseInt(strDate.substring(0, 4));
		month = Integer.parseInt(strDate.substring(4, 6))-1;
		day = Integer.parseInt(strDate.substring(6, 8));
		hour = Integer.parseInt(strDate.substring(8, 10)) + 9;	// GMT 기준 시간에 +9
		min = Integer.parseInt(strDate.substring(10, 12));
		sec = Integer.parseInt(strDate.substring(12, 14));

		
		SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, "KST");
		
		Calendar cal = Calendar.getInstance(timeZone);
		cal.set(year, month, day, hour, min, sec);
		
		return setDateFormat(cal, dateType);
	}
	
	/**
	 * 날짜문자열을 여러가지 형태로 변환
	 * 
	 * @param dateType	날짜 포맷 타입 
	 * @return 변환된 형태의 날짜
	 */
	public static String getFormattedDate(String dateType) {
        String DATE_FORMAT = "yyyyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleTimeZone timeZone = new SimpleTimeZone(0, "KST");
        sdf.setTimeZone(timeZone);

        long time = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(time);
		String currentDate = sdf.format(date);
		
        return setDateFormat(currentDate.substring(0, 8) + "T" + currentDate.substring(8, 14) + "Z", dateType);
	}
	
	public static Calendar getCalendar() {
		
		SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, "KST");
		
		return Calendar.getInstance(timeZone);
	}
	
	/**
	 * Calendar type을 String type으로 변환시킨다.
	 *
	 * @param cal Calendar type date
	 * @return String 으로 변환된 date ("yyyy-mm-dd hh:mi:ss")
	 */
	public static String getDateTimeString(Calendar cal){
		
        String DATE_FORMAT = "yyyy" + dateDelim + "MM" + dateDelim + "dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleTimeZone timeZone = new SimpleTimeZone( 9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);
		Date idate = cal.getTime();
        return sdf.format(idate);
	}

	/**
	 * Calendar type을 String type으로 변환시킨다.
	 *
	 * @param date Date type date
	 * @return String 으로 변환된 date ("yyyy-mm-dd hh:mi:ss")
	 */
	public static String getDateTimeString(Date date){
		
        String DATE_FORMAT = "yyyy" + dateDelim + "MM" + dateDelim + "dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleTimeZone timeZone = new SimpleTimeZone( 9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);
		return sdf.format(date);
	}
	
	/**
	 * Calendar type을 String type으로 변환시킨다.
	 *
	 * @param date Date type date
	 * @return String 으로 변환된 date ("yyyymmdd")
	 */
	public static String getDateString(Date date){
		
        String DATE_FORMAT = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleTimeZone timeZone = new SimpleTimeZone( 9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);
		return sdf.format(date);
	}

	/**
	 * 현재 시각을 String type으로 변환시킨다.
	 *
	 * @return String 으로 변환된 date ("yyyy-mm-dd hh:mi:sss")
	 */
    public static String getCurrentTime(){
		return getCurrentTime(1);
    }

	/**<pre>
	 *	현재 시각을 String type으로 변환시킨다.
	 *  	Type : 1 ==> ("yyyy-mm-dd hh:mi:sss")
	 *  	Type : 0 or else ==> ("yyyymmddhhmisss")
	 *
	 * @return String 으로 변환된 date </pre>
	 */
	public static String getCurrentTime(int type) {
        String DATE_FORMAT = "";
        
		if (type == 1) {
	        DATE_FORMAT = "yyyy" + dateDelim + "MM" + dateDelim + "dd HH:mm:ss,SSS";
		} else if (type == 0) {
	        DATE_FORMAT = "yyyyMMddHHmmssSSS";
		} else {
	        DATE_FORMAT = "yyyyMMddHHmmssSSS";
		}
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleTimeZone timeZone = new SimpleTimeZone( 9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);

        long time = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(time);
        return sdf.format(date);
	}

	/**
	 * 현재 날짜를 String type으로 변환시킨다.
	 *
	 * @return String 으로 변환된 date ("yyyymmdd")
	 */
    public static String getDate(){
		return getCurrentTime(0).substring(0, 8);
    }

	/**
	 * 현재 시각을 String type으로 변환시킨다.
	 *
	 * @return String 으로 변환된 date ("yyyymmddhhmi")
	 */
    public static String getDateTime(){
		return getCurrentTime(0).substring(0, 12);
    }

	/**
	 * long 타입의 날짜를 YYYYMMDDhh24missSSS 형식으로 변환
	 * @param timeMillis	날짜
	 * @return
	 */
	public static String toDateTimeStr(long timeMillis) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);

        java.util.Date date = new java.util.Date(timeMillis);
        return sdf.format(date);
	}

    /**<pre>
	 * 현재 시각에서 시간을 더하고 뺀 날짜를 얻는다.
	 *
	 * @param	hours	시간
	 * @return String 으로 변환된 date </pre>
	 */
	public static String addHours(int hours) {
		String strDate = getCurrentTime(0);
		return addHours(strDate.substring(0, 12), hours);
	}

	/**<pre>
	 * 주어진 날짜에서 시간 수를 더하고 뺀 날짜를 얻는다.
	 *
	 * @param	strDateTime		YYYYMMDDHHMI 형태의 날짜 문자열
	 * @param	hours		시간
	 * @return String 으로 변환된 date </pre>
	 */
	public static String addHours(String strDateTime, int hours) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		ParsePosition pos = new ParsePosition(0);
		
		String getStrDateTime = strDateTime;
		
		if(strDateTime.length() > 12) {
			getStrDateTime = strDateTime.substring(0, 10).replace("-", "") + strDateTime.substring(11, 16).replace(":", "");
		}
		
		Date date1 = formatter.parse(getStrDateTime, pos);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date1);

		calendar.add(GregorianCalendar.HOUR, hours);

		String resultDate = formatter.format(calendar.getTime());
		
		if (strDateTime.length() > 12) {
			resultDate = resultDate.substring(0, 4) + "-" + resultDate.substring(4, 6) + "-" + resultDate.substring(6, 8) + " " + resultDate.substring(8, 10) + ":" + resultDate.substring(10, 12);
		}
		return resultDate;
	}

    /**<pre>
	 * 현재 시각에서 날 수를 더하고 뺀 날짜를 얻는다.
	 *
	 * @param	days	날 수
	 * @return String 으로 변환된 date </pre>
	 */
	public static String addDays(int days) {
		String strDate = getCurrentTime(0);
		return addDays(strDate.substring(0, 8), days);
	}

	/**<pre>
	 * 주어진 날짜에서 날 수를 더하고 뺀 날짜를 얻는다.
	 *
	 * @param	strDate		YYYYMMDD 형태의 날짜 문자열
	 * @param	days		날 수
	 * @return String 으로 변환된 date </pre>
	 */
	public static String addDays(String strDate, int days) {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
		ParsePosition pos = new ParsePosition(0);
		
		String getStrDate = strDate;
		
		if(strDate.length() > 8) {
			getStrDate = strDate.substring(0, 10).replace("-", "");
		}
		
		Date date1 = formatter.parse(getStrDate, pos);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date1);

		calendar.add(GregorianCalendar.DATE, days);

		String resultDate = formatter.format(calendar.getTime());
		
		if(strDate.length() > 8) {
			resultDate = resultDate.substring(0, 4) + "-" + resultDate.substring(4, 6) + "-" + resultDate.substring(6, 8);
		}
		return resultDate;
	}

	/**
	 * 현재 날짜에서 달을 더하고 뺀 월을 얻는다. (YYYYMM)
	 * @param months	달 수
	 * @return
	 */
	public static String addMonths(int months) {
		String strDate = getCurrentTime(0);
		strDate = strDate.substring(0, 8);

		return addMonths(strDate, months);
	}

	/**
	 * 주어진 날짜에서 달을 더하고 뺀 월을 얻는다. (YYYYMMDD)
	 * @param strDate	YYYYMMDD 형태의 날짜 문자열
	 * @param months	달 수
	 * @return
	 */
	public static String addMonths(String strDate, int months) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		ParsePosition pos = new ParsePosition(0);
		
		int strDateLength = strDate.length();
		
		if(strDateLength > 8) {
			strDate = Tools.replaceStr(strDate, "-", "");
		}else if(strDateLength == 6){
			strDate = strDate+"01";		//yyyyMM 형태의 경우 일을 01로 가정하고 1달을 더해준다.
		}
		
		Date date1 = formatter.parse(strDate, pos);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date1);

		calendar.add(GregorianCalendar.MONTH, months);

		String resultDate = formatter.format(calendar.getTime());
		
		if(strDateLength > 8) {
			resultDate = Tools.cutLeft(resultDate, 4) + "-" + Tools.cutRight(Tools.cutLeft(resultDate, 6), 2) + "-" + Tools.cutRight(resultDate, 2);
		}else if(strDateLength == 6){
			resultDate = Tools.cutLeft(resultDate, 6);
		}
		return resultDate;
	}

	/**<pre>
	 * 주어진 날짜에서 분을 더하고 뺀 날짜를 얻는다.
	 *
	 * @param	strDate		yyyyMMddHHmm 형태의 날짜 문자열
	 * @param	mins	분
	 * @return String 으로 변환된 date </pre>
	 */
	public static String addMinutes(String strDate, int mins) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		ParsePosition pos = new ParsePosition(0);
		
		Date date1 = formatter.parse(strDate, pos);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date1);

		calendar.add(GregorianCalendar.MINUTE, mins);

		String resultDate = formatter.format(calendar.getTime());
		
		return resultDate;
	}

	/**
	 * 시작 시간과 종료시간을 입력하여 IntervalTime을 얻는다.
	 *
	 * ex) 00:01:123 (00분 01초 123)
	 */
	public static String getIntervalTime(String startTime, String endTime) {
		String Interval = Tools.iTOs(Tools.sTOi(endTime.substring(10)) - Tools.sTOi(startTime.substring(10)));
		int len = Interval.length();
		String header = "";
		for (int i=7; i > len; i--) {	header = header+"0";	}
		String TempTime = header + Interval;
		String IntervTime = TempTime.substring(0,2)+":"+TempTime.substring(2,4)+":"+TempTime.substring(4);
		return IntervTime;
	}

	/**
	 * YYYYMMDDHHMMSSSSS or YYMMDDHHMMSSSSS 의 Type으로 들어온 String값을 HH:MM:SS:SSS Type으로 변환시킨다.
	 */
	public static String getTimeStampType(String strFullTime) {
		String strTemp = "";
		int length = strFullTime.trim().length();
		if (length == 9) {
			strTemp = strFullTime;
		} else if (length == 15) {
			strTemp = strFullTime.substring(6);
		} else if (length == 17) {
			strTemp = strFullTime.substring(8);
		} else {
			strTemp = "000000000";
		}
		return strTemp.substring(0,2)+":"+strTemp.substring(2,4)+":"+strTemp.substring(4,6)+"."+strTemp.substring(6);
	}

	/**
	 * Date 타입의 날짜 문자열을 날짜 까지만 가져온다.
	 *
	 * @param	d	날짜문자열 (1899-12-31 00:00:00.0)
	 * @return	날짜문자열 (1899-12-31)
	 */
	public static String getDateStr(String d) {
		if (d == null) {
			return "";
		} else if (d.length() <= 19) {
			return d;
		} else {
			return d.substring(0, 19);
		}
	}

	/**
	 * Date 타입의 날짜 문자열을 시, 분, 초 까지만 가져온다.
	 *
	 * @param	d	날짜문자열 (1899-12-31 00:00:00.0)
	 * @return	날짜문자열 (1899-12-31 00:00:00)
	 */
	public static String getDateTimeStr(String d) {
		if (d == null) {
			return "";
		} else if (d.length() <= 19) {
			return d;
		} else {
			return d.substring(0, 19);
		}
	}

	/**
	 * YYYYMMDDHHMISS 또는 YYYYMMDD, yyyyMM 형태의 날짜 문자열을 YYYY-MM-DD 형태로 바꿔준다.
	 *
	 * @param	d	날짜문자열
	 * @return	날짜문자열
	 */
	public static String setDateFormat(String d) {
		if (d == null) {
			return "";
		} else if (d.length() == 6) {
			return d.substring(0, 4) + "-" + d.substring(4, 6);
		} else if (d.length() == 8) {
			return d.substring(0, 4) + dateDelim + d.substring(4, 6) + dateDelim + d.substring(6, 8);
		} else if (d.length() == 10) {
			return d.substring(0, 4) + dateDelim + d.substring(5, 7) + dateDelim + d.substring(8, 10);
		} else if (d.length() >= 19) {
			return d.substring(0, 4) + dateDelim + d.substring(5, 7) + dateDelim + d.substring(8, 10);
		} else {
			return d;
		}
	}

	/**
	 * YYYYMMDDHHMISS형태의 날짜 문자열을 YYYY-MM-DD HH:MI:SS 형태로 바꿔준다.
	 *
	 * @param	d	날짜문자열
	 * @return	날짜문자열
	 */
	public static String setDateTimeFormat(String d) {
		if (d == null) {
			return "";
		} else if (d.length() == 12) {
			return d.substring(0, 4) + dateDelim + d.substring(4, 6) + dateDelim + d.substring(6, 8) + " " + d.substring(8, 10)  + ":" + d.substring(10, 12);
		} else if (d.length() == 14) {
			return d.substring(0, 4) + dateDelim + d.substring(4, 6) + dateDelim + d.substring(6, 8) + " " + d.substring(8, 10)  + ":" + d.substring(10, 12) + ":" + d.substring(12, 14);
		} else if (d.length() >= 19) {
			return d.substring(0, 4) + dateDelim + d.substring(5, 7) + dateDelim + d.substring(8, 10) + " " + d.substring(11, 13)  + ":" + d.substring(14, 16) + ":" + d.substring(17, 19);
		} else {
			return d;
		}
	}

	/**
	 * 날짜 문자열을 YY-MM-DD [HH:MI:SS] 형태로 바꿔준다. <BR>
	 * 금일인 경우 [HH:MI:SS] 형태만 표시한다. <BR>
	 * (DB에서 읽어온 날짜문자열을 화면에 출력하는데 사용) <BR>
	 * @param	d	날짜문자열
	 * @return	날짜문자열
	 */
	public static String setDateTimeFormat2(String d) {
		String today = getCurrentTime(0).substring(0, 8);	// YYYYMMDD
		String formatDate = null;
		if (d == null) {
			formatDate = "";
		} else if (d.length() == 12) {	// YYYYMMDDHHMI
			if (today.equals(d.substring(0, 8))) {
				formatDate = "[" + d.substring(8, 10) + ":" + d.substring(10, 12) + "]";
			} else {
				formatDate = d.substring(2, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8) + " [" + d.substring(8, 10) + ":" + d.substring(10, 12) + "]";
			}
		} else if (d.length() == 14) {	// YYYYMMDDHHMISS
			if (today.equals(d.substring(0, 8))) {
				formatDate = "[" + d.substring(8, 10)  + ":" + d.substring(10, 12) + ":" + d.substring(12, 14) + "]";
			} else {
				formatDate = d.substring(2, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8) + " [" + d.substring(8, 10)  + ":" + d.substring(10, 12) + ":" + d.substring(12, 14) + "]";
			}
		} else if (d.length() == 19) {	// YYYY-MM-DD HH:MI:SS
			if (Tools.setFormat(today, "####-##-##").equals(d.substring(0, 10))) {
				formatDate = "[" + d.substring(11, 19) + "]";
			} else {
				formatDate = d.substring(2, 10) + " [" + d.substring(11, 19) + "]";
			}
		} else {
			formatDate = d;
		}
		return formatDate;
	}
	
	/**
	 * 날짜 문자열을 YY.MM.DD 형태로 바꿔준다.
	 * 
	 * @param d 날짜 문자열
	 * @return 날짜문자열
	 */
	
	public static String setDateTimeFormat3(String d) {
		String formatDate = null;
		
		if (d == null) {
			formatDate = "";
		} else if(d.length() == 16 || d.length() == 8) {
			formatDate = d.substring(0, 4) + "." + d.substring(4, 6) + "." + d.substring(6, 8);
		} else {
			formatDate = d;
		}
		
		return formatDate;
	}
	
	/**
	 * 날짜 문자열을 YY.MM.DD HH:mm 형태로 바꿔준다.
	 * 
	 * @param d 날짜 문자열
	 * @return 날짜 문자열
	 */
	public static String setDateTimeFormat4(String d) {
		String formatDate = null;
		
		if (d == null) {
			formatDate = "";
		} else if(d.length() == 16) {
			formatDate = d.substring(0, 4) + "." + d.substring(4, 6) + "." + d.substring(6, 8) + " " + d.substring(9, 11) + ":" + d.substring(11, 13);
		} else {
			formatDate = d;
		}
		
		return formatDate;
	}

	/**<pre>
	 * 날짜 간격을 구한다.
	 *
	 * @param	strDate1	시작일자(YYYYMMDD)
	 * @param	strDate2	종료일자(YYYYMMDD)
	 * @return	날짜 간격 </pre>
	 */
	public static int getDaysBetween(String strDate1, String strDate2) {
		int daysBetween = 0;
		long interval = 0L;
		
		if (strDate1 != null && strDate1.length() == 8 && strDate2 != null && strDate2.length() == 8) {
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
			ParsePosition pos1 = new ParsePosition(0);
			ParsePosition pos2 = new ParsePosition(0);

			Date date1 = formatter.parse(strDate1, pos1);
			Date date2 = formatter.parse(strDate2, pos2);

			interval = date2.getTime() - date1.getTime();
		}
		daysBetween = new Long(interval / (24 * 60 * 60 * 1000)).intValue();
		

		return daysBetween;
	}

	/**
	 * 월 수를 구한다.
	 * @param	fromYYYYMM	시작연월(YYYYMM)
	 * @param	toYYYYMM		종료연월(YYYYMM)
	 * @return	날짜 간격 
	 */
	public static int getMonthsBetween(String fromYYYYMM, String toYYYYMM) {
		int monthsBetween = 0;

		if (fromYYYYMM == null || toYYYYMM == null || fromYYYYMM.length() != 6 || toYYYYMM.length() != 6) {
			return -1;
		}
		
		int from = Integer.parseInt(fromYYYYMM);
		int to = Integer.parseInt(toYYYYMM);
		
		if (from > to) {
			fromYYYYMM = String.valueOf(to);
			toYYYYMM = String.valueOf(from);
		}
	
		int fromYear = Integer.parseInt(fromYYYYMM.substring(0, 4));
		int toYear = Integer.parseInt(toYYYYMM.substring(0, 4));
		int fromMonth = Integer.parseInt(fromYYYYMM.substring(4, 6));
		int toMonth = Integer.parseInt(toYYYYMM.substring(4, 6));
		
		if (fromYear == toYear) {
			monthsBetween = toMonth - fromMonth + 1;
		} else {
			monthsBetween = (12 - fromMonth + 1) + toMonth + (toYear - fromYear - 1) * 12;
		}
		

		return monthsBetween;
	}

	/**
	 * 해당월의 마지막 날을 YYYYMMDD 형태로 리턴
	 * @param	yyyymm		YYYYMM 형태의 날짜 문자열
	 * @return String 으로 변환된 date
	 */
	public static String getLastDay(String yyyymm) {
		String lastDate = null;
		
		int year = Integer.parseInt(yyyymm.substring(0, 4));
		int month = Integer.parseInt(yyyymm.substring(4, 6));
		int day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			if ((year % 4) == 0) {
				if ((year % 100) == 0 && (year % 400) != 0) {
					day = 28;
				} else {
					day = 29;
				}
			} else {
				day = 28;
			}
			break;
		default:
			day = 30;
		}

		lastDate = yyyymm + (day < 10 ? "0" : "") + String.valueOf(day);
		
		return lastDate;
	}
	
	/**
	 * @param strDate 날짜 (2013-01-17T17:09:54.191+09:00 형식)
	 * @return 13.01.17 오후 17:09 형식으로 표시
	 */
	public static String getDateToStr(String strDate, Locale locale)
	{
		String date = "";
		int year, month, day, hour, min, sec;

		year = Integer.parseInt(strDate.substring(0, 4));
		month = Integer.parseInt(strDate.substring(5, 7))-1;
		day = Integer.parseInt(strDate.substring(8, 10));
		hour = Integer.parseInt(strDate.substring(11, 13));
		min = Integer.parseInt(strDate.substring(14, 16));
		sec = Integer.parseInt(strDate.substring(17, 19));
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.set(year, month, day, hour, min, sec);
	    SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd a HH:mm", Locale.getDefault());
		Date idate = cal.getTime();
		date =  sdf.format(idate);	
		
		return date;
	}
	
	/**
	 * @param strDate 날짜 (2013-01-17T17:09:54.191+09:00 형식)
	 * @return 13.01.17 오후 17:09 형식으로 표시
	 */
	public static String getDateToStr(String strDate, String langCode)
	{
		String date = "";
		int year, month, day, hour, min, sec;
		
		year = Integer.parseInt(strDate.substring(0, 4));
		month = Integer.parseInt(strDate.substring(5, 7))-1;
		day = Integer.parseInt(strDate.substring(8, 10));
		hour = Integer.parseInt(strDate.substring(11, 13));
		min = Integer.parseInt(strDate.substring(14, 16));
		sec = Integer.parseInt(strDate.substring(17, 19));
		
		Locale locale = Locale.ENGLISH;	//Locale.getDefault(); 
		if (Tools.isEqual(langCode, "en")) {
			locale = Locale.ENGLISH;
		} else if (Tools.isEqual(langCode, "ko")) {
			locale = Locale.KOREAN;
		}
		
		Calendar cal = Calendar.getInstance(locale);
		cal.set(year, month, day, hour, min, sec);
	    SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd a HH:mm", locale);
		Date idate = cal.getTime();
		date =  sdf.format(idate);
		
		return date;
	}
	
	/**
	 * Calendar 데이터를 XMLGregorianCalendar 타입으로 변환
	 * @param Calendar
	 * @return
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		
		XMLGregorianCalendar xmlCal = null;
		
		try {
			xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			
			xmlCal.setYear(calendar.get(Calendar.YEAR));
			xmlCal.setMonth(calendar.get(Calendar.MONTH) + 1);
			xmlCal.setDay(calendar.get(Calendar.DAY_OF_MONTH));
			xmlCal.setHour(calendar.get(Calendar.HOUR_OF_DAY));
			xmlCal.setMinute(calendar.get(Calendar.MINUTE));
			xmlCal.setSecond(calendar.get(Calendar.SECOND));
			xmlCal.setMillisecond(calendar.get(Calendar.MILLISECOND));
		} catch (DatatypeConfigurationException e) {
			logger.error("DatatypeConfigurationException", e);
		}
	
		return xmlCal;
	}
	
	public static String getCurrentDate(){
		return getCurrentDate(dateDelim);
	}
	
	public static String getCurrentDate(String deleim){
		
		String DATE_FORMAT = "yyyy" + deleim + "MM" + deleim + "dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleTimeZone timeZone = new SimpleTimeZone( 9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);

        long time = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(time);
        return sdf.format(date);
	}
	
   /**
    * 해당 일자가 속한 주의 첫날(기준:월요일)
    * 
    * @param sDate	YYYYMMDD 형식의 날짜
    * @returns {String}
    */
	public static String getWeekFirstDate(String sDate) {
		int yy = 0;
		int mm = 0;
		int dd = 0;
	   	if (sDate.length() == 8) {
	   	    yy = Integer.parseInt(sDate.substring(0, 4));
	   	    mm = Integer.parseInt(sDate.substring(4, 6));
	   	    dd = Integer.parseInt(sDate.substring(6, 8));
	   	} else if (sDate.length() == 10) {
	   	    yy = Integer.parseInt(sDate.substring(0, 4));
	   	    mm = Integer.parseInt(sDate.substring(5, 7));
	   	    dd = Integer.parseInt(sDate.substring(8, 10));
	   	} else {
	   		return "";
	   	}
	   	
	   	Calendar cal = Calendar.getInstance(SimpleTimeZone.getDefault());
		cal.set(yy, mm - 1, dd, 0, 0, 0);
    
		// cal.get(Calendar.DAY_OF_WEEK) : 일(1),월(2),화(3),수(4),목(5),금(6),토(7)

       String firstDate = null;
       if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
    	   firstDate = sDate;
       } else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
    	   firstDate = addDays(sDate, -6);
       } else {
    	   firstDate = addDays(sDate, -1 * (cal.get(Calendar.DAY_OF_WEEK) - 1));
       }
    
       return firstDate;
   }

	/**
	 * YYYY-MM-DD 형태의 날짜 문자열을 YYYYMMDD 형태로 변경한다.
	 *
	 * @param	strDate	날짜문자열 (YYYY-MM-DD)
	 * @return	YYYYMMDD 날짜 문자열
	 */
	public static String toYYYYMMDD(String strDate) throws NumberFormatException {
		if (strDate == null)
			return null;
		
		strDate = strDate.replace("-", "");
		if (strDate.length() == 0)
			return null;
			
		if (strDate.length() !=8)
			throw new NumberFormatException("변경된 포멧이 8자리가 아닙니다.");
		
		return strDate;
	}
	
	/**
	 * YYYY-MM 형태의 문자열을 YYYYMM 형태로 변경한다.
	 * 
	 * @param yrMnth
	 * @return
	 */
	public static String toYYYYMM(String yrMnth) {
		if (yrMnth == null)
			return null;
		
		yrMnth = yrMnth.replace("-", "");
		if (yrMnth.length() == 0)
			return null;
			
		if (yrMnth.length() !=6)
			throw new NumberFormatException("변경된 포멧이 6자리가 아닙니다.");
		
		return yrMnth;
	}
	
	/**
	 * simpledateformat parse 래핑.
	 * 
	 * @param pattern 날짜 패턴
	 * @param source 문자열
	 * @return Calendar.
	 * @throws ParseException
	 */
	static public Calendar parse(String pattern, String source) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		SimpleTimeZone timeZone = new SimpleTimeZone( 9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);
		Date d = sdf.parse(source);
		Calendar cal = getCalendar();
		cal.setTime(d);
		return cal;
	}
	
	/**
	 * simpledateformat format 래핑.
	 * 
	 * @param pattern 날짜 패턴
	 * @param cal 날짜
	 * @return 
	 */
	static public String format(String pattern, Calendar cal) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		SimpleTimeZone timeZone = new SimpleTimeZone( 9 * millisPerHour, "KST");
        sdf.setTimeZone(timeZone);
        
		return sdf.format(cal.getTime());			
	}
}
