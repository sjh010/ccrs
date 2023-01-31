package com.mobileleader.edoc.monitoring.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tools {
	
	final static Locale currentLocale = Locale.KOREA;
	final static NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);

	private static final Logger logger = LoggerFactory.getLogger(Tools.class);
	
	/**
	 * Parameter를 읽어 URL 형태의 문자열로 반환
	 *
	 * @param request HttpServletRequest
	 * @return
	 */
	public static String getParameter(HttpServletRequest request) {
		String params = "";

		@SuppressWarnings("rawtypes")
		Enumeration enumeration = request.getParameterNames();
		String param = null, value = null;
		
		logger.debug("========== PARAM start ==========");
		while (enumeration.hasMoreElements()) {
			param = (String) enumeration.nextElement();
			value = toNotNull(request.getParameter(param));
			params += (params.equals("") ? "" : "&") + param + "=" + value;
			logger.debug(param + "=" + value);
		}
		logger.debug("========== PARAM end ==========");

		return params;
	}
	
	/**
	 * 문자열을 equals 비교. 값이 NULL인 경우 ""로 대체해 비교
	 * @param str1 문자열
	 * @param str2	비교 문자열
	 * @return boolean
	 */
	public static boolean isEqual(String str1, String str2) {
		str1 = toNotNull(str1);
		str2 = toNotNull(str2);
		return str1.equals(str2);
	}
	
	/**
	 * 문자형 객체가 Null(null, "null")일 경우 "" 값으로 세팅
	 *
	 * @param	value			String 객체
	 * @return	String
	 */
	public static String toNotNull(String value) {
		return toNotNull(value, "");
	}

	/**
	 * 문자형 객체가 Null(null, "null", "")일 경우 디폴트 값으로 세팅
	 *
	 * @param	value			String 객체
	 * @param	defaultValue	default 값
	 * @return	String
	 */
	public static String toNotNull(String value, String defaultValue) {
		String returnValue;
		if (value == null || value.trim().equals("") || value.trim().equals("null")) {
			returnValue = defaultValue;
		} else {
			returnValue = value;
		}
		return returnValue;
	}
	
	/**
	 * String 문자열의 oldString을 newString으로 바꾼다.
	 *
	 * @param str 바꿀 문자열.
	 * @param oldStr 과거의 문자열.
	 * @param newStr 새로운 문자열.
	 * @return 바뀐 문자열.
	 */
	public static String replaceStr(String str, String oldStr, String newStr) {
		if (str == null) {
			return "";
		}

		int len = str.length() + (newStr.length() - oldStr.length()) * (str.length() / oldStr.length());
		StringBuffer retBuf = new StringBuffer(len);

		int pos = 0;
		int index = 0;

		while ((pos = str.indexOf(oldStr, index)) != -1) {
			retBuf.append(str.substring(index, pos));
			retBuf.append(newStr);
			index = pos + oldStr.length();
		}
		retBuf.append(str.substring(index));

		return retBuf.toString();
	}

	
	public static String replaceBr(String str) {
		if(str == null) {
			return "";
		}
		
		str = str.replaceAll("\r\n", "<br/>");	// Windows
		str = str.replaceAll("\n", "<br/>");	// Unix
		str = str.replaceAll("\r", "<br/>");	// Mac
		
		return str;
	}
	
	public static boolean isNullOrEmpty(String str) {
		if(str == null) {
			return true;
		}
		return str.isEmpty();
	}
	
	/**
	 * 문자열을 길이만큼 앞에서부터 잘라서 반환
	 *
	 * @param str 문자열
	 * @param len 길이
	 * @return 잘라진 문자열
	 */
	public static String cutLeft(String str, int len) {
		String ret = null;
		str = toNotNull(str);
		if (str.length() <= len) {
			ret = str;
		} else {
			ret = str.substring(0, len);
		}
		return ret;
	}

	/**
	 * 문자열을 길이만큼 뒤에서부터 잘라서 반환
	 *
	 * @param str 문자열
	 * @param len 길이
	 * @return 잘라진 문자열
	 */
	public static String cutRight(String str, int len) {
		String ret = null;
		str = toNotNull(str);
		if (str.length() <= len) {
			ret = str;
		} else {
			ret = str.substring(str.length() - len);
		}
		return ret;
	}


	/**
	 * String형을 Integer형으로 바꾼다.
	 *
	 * @param str1 int로 바꿀 문자열
	 * @return int 바뀌어진 int값
	 */
	public static int sTOi(String str1) {
		int retInt = Integer.parseInt(str1);
		return retInt;
	}

	/**
	 * String형을 Integer형으로 바꾼다.
	 *
	 * @param str int로 바꿀 문자열
	 * @param defValue Null이나 Null String이 입력되었을때 Return할 기본값
	 * @return int 바뀌어진 int값
	 */
	public static int sTOi(String str, int defValue) {
		int retInt = 0;
		if ((str == null) || (str.equals(""))) {
			retInt = defValue;
		} else {
			retInt = Integer.parseInt(str);
		}
		return retInt;
	}

	/**
	 * String형을 long형으로 바꾼다.
	 *
	 * @param str long로 바꿀 문자열
	 * @return long 바뀌어진 long값
	 */
	public static long sTOl(String str) {
		long retLong = Long.parseLong(str);
		return retLong;
	}

	/**
	 * String형을 long형으로 바꾼다.
	 *
	 * @param str long로 바꿀 문자열
	 * @param defValue Null이나 Null String이 입력되었을때 Return할 기본값
	 * @return long 바뀌어진 long값
	 */
	public static long sTOl(String str, long defValue) {
		long retLong = 0;
		if ((str == null) || (str.equals(""))) {
			retLong = defValue;
		} else {
			retLong = Long.parseLong(str);
		}
		return retLong;
	}
	
	/**
	 * String형을 float형으로 바꾼다.
	 *
	 * @param str float로 바꿀 문자열
	 * @param defValue Null이나 Null String이 입력되었을때 Return할 기본값
	 * @return float 바뀌어진 float값
	 */
	public static float sTOf(String str, float defValue) {
		float retLong = 0;
		if ((str == null) || (str.equals(""))) {
			retLong = defValue;
		} else {
			retLong = Float.parseFloat(str);
		}
		return retLong;
	}

	/**
	 * HexString형을 Decimal String형으로 바꾼다.
	 *
	 * @param str int로 바꿀 문자열
	 * @return Decimal형의 String값
	 */
	public static String hTOs(String str) {
		int retInt = 0;
		try {
			retInt = Integer.parseInt(str, 16);
		} catch (NumberFormatException e) {
			retInt = 0;
		}
		String retstr = Integer.toString(retInt);
		return retstr;
	}

	/**
	 * int형을 String형으로 바꾼다.
	 *
	 * @param intValue String로 바꿀 int값
	 * @return String로 바뀌어진 int값
	 */
	public static String iTOs(int intValue) {
		String retstr = Integer.toString(intValue);
		return retstr;
	}

	/**
	 * long형을 String형으로 바꾼다.
	 *
	 * @param longValue String로 바꿀 long값
	 * @return String로 바뀌어진 long값
	 */
	public static String lTOs(long longValue) {
		String retstr = Long.toString(longValue);
		return retstr;
	}

	/**
	 * Integer형을 String형으로 바꾼다.
	 *
	 * @param intvalue String로 바꿀 int값
	 * @return String로 바뀌어진 int값
	 */

	/**
	 * Integer형을 HexString형으로 바꾼다.
	 *
	 * @param intvalue String로 바꿀 int값
	 * @return HexString HexString로 바뀌어진 int값
	 */
	public static String iTOh(int intvalue) {
		String ret = Integer.toHexString(intvalue);
		return ret;
	}

	/**
	 * [1,234,567]형태의 String값을 double값으로 변환한다.
	 *
	 * @param strValue 변환시킬 String값
	 * @return double 변환된 double값.
	 */
	public static double sTOd(String strValue) {
		String retvalue = Tools.strRetTokenizer(strValue, ",");
		double Value = Double.parseDouble(retvalue);
		return Value;
	}

	/**
	 * double형의 데이터를 String형으로 Converting 시켜서 Return해주는 Method <BR>
	 * [사용예]<BR>
	 * double dbl = 1234567890.123; <BR>
	 * 화면출력시 1.23456789123E9 <BR>
	 * (type1) String str = 1,234,567,890.123 <BR>
	 * (type2) String str = 1,234,567,890 <BR>
	 * (type3) String str = 1234567890 <BR>
	 *
	 * @param dblval 더블형의 입력값
	 * @param type 포맷의 type
	 * @return String returnString
	 */
	public static String dTOs(double dblval, int type) {
		DecimalFormat df = null;
		String dblstr = "";
		String retstr = "";

		df = new java.text.DecimalFormat("#,###,##0.#####");
		dblstr = df.format(dblval);

		if (type == 1) {
			retstr = dblstr;
		} else if (type == 2) {
			if (dblstr.indexOf(".") != -1) {
				retstr = dblstr.substring(0, dblstr.indexOf("."));
			} else {
				retstr = dblstr;
			}
		} else if (type == 3) {
			String strTemp = Tools.strRetTokenizer(dblstr, ",");
			if (strTemp.indexOf(".") != -1) {
				retstr = strTemp.substring(0, strTemp.indexOf("."));
			} else {
				retstr = strTemp;
			}
		} else {
			retstr = dblstr;
		}

		if (retstr.equals("-0"))
			retstr = "0";

		return retstr;
	}

	/**
	 * Double형의 데이터를 String형으로 Converting 시켜서 Return해주는 Method<BR>
	 * [사용예] <BR>
	 * double dbl = 1234567890.123;<BR>
	 * Double DBL = new Double(dbl);<BR>
	 * 화면출력시 1.23456789123E9<BR>
	 * (type1) String str = 1,234,567,890.123<BR>
	 * (type2) String str = 1,234,567,890<BR>
	 * (type3) String str = 1234567890<BR>
	 *
	 * @param Dblvalue DBL(더블형의 입력값)
	 * @param type (포맷의 type)
	 * @return String returnString
	 */
	public static String dTOs(Double Dblvalue, int type) {
		double dblvalue = 0.0;

		if (Dblvalue != null) {
			dblvalue = Dblvalue.doubleValue();
		}
		return dTOs(dblvalue, type);
	}
	
	/**
	 * Delimeter로 구분되어진 String 객체를 delim으로 구분지어서 하나씩 값을 잘라낸다.
	 *
	 * @param str
	 * @param delim Delimeters
	 * @return String 리턴 String값
	 * @exception StringIndexOutOfBoundsException
	 */
	public static String strRetTokenizer(String str, String delim) throws StringIndexOutOfBoundsException {
		String retStr = "";
		if (str != null && !str.equals("")) {
			StringTokenizer st = new StringTokenizer(str, delim);
			int stcount = st.countTokens();

			for (int i = 1; i <= stcount; i++) {
				if (st.hasMoreElements()) {
					retStr = retStr + st.nextToken();
				}
			}
		} else {
			retStr = "";
		}
		return retStr;
	}

	/**
	 * setFormat("123456789". "##/## : ##" ) -->12/34:56
	 *
	 * @param str
	 * @param form
	 * @return String
	 */
	public static String setFormat(String str, String form) {

		if (str == null || str.length() == 0)
			return str;
		char[] returnStr = new char[form.length()];

		for (int i = 0, j = 0; i < form.length(); i++, j++) {
			if (form.charAt(i) == '#') {
				try {
					returnStr[i] = str.charAt(j);
				} catch (StringIndexOutOfBoundsException e) {
					returnStr[i] = '\u0000';
				}
			} else {
				returnStr[i] = form.charAt(i);
				--j;
			}
		}
		return new String(returnStr);
	}

	/**
	 * 날짜 표시 형태로 변환
	 *
	 * @param str YYYYMMDD 형태의 날짜 문자열
	 * @return String
	 */
	public static String setDateFormat(String str) {
		return setFormat(str, "####-##-##");
	}

	/**
	 * 숫자에 3자리마다 comma를 붙인다.
	 *
	 * @param number
	 * @return String
	 */
	public static String comma(int number) {
		return numberFormatter.format(number);
	}
	
	/**
	 * 숫자에 3자리마다 comma를 붙인다.
	 *
	 * @param number
	 * @return String
	 */
	public static String comma(double number) {
		return numberFormatter.format(number);
	}

	/**
	 * 숫자에 3자리마다 comma를 붙인다.
	 *
	 * @param number
	 * @return String
	 */
	public static String comma(long number) {
		return numberFormatter.format(number);
	}

	/**
	 * 파일크기를 단위와 함께 문자열로 리턴
	 *
	 * @param fileSize 파일크기
	 * @return String
	 */
	public static String getFileSize(long fileSize) {
		String fileUnit = "Byte";
		
		try {
			if (fileSize >= 1024 * 1024 * 1024) {
				fileSize = Math.round(fileSize / (1024.0 * 1024 * 1024));
				DecimalFormat df = new DecimalFormat("#.00");
				fileUnit = df.format(fileSize) + "GB";
			} else if (fileSize >= 1024 * 1024) {
				fileSize = Math.round(fileSize / (1024.0 * 1024));
				DecimalFormat df = new DecimalFormat("#.00");
				fileUnit = df.format(fileSize) + "MB";
			} else if (fileSize >= 1024) {
				fileSize = Math.round(fileSize / 1024.0);
				DecimalFormat df = new DecimalFormat("#.0");
				fileUnit = df.format(fileSize) + "KB";
			} else {
				fileUnit = fileSize + "Bytes";
			}
		}catch (ArithmeticException ae) {
			logger.error(ae.getMessage(), ae);
		}
		return fileUnit;
	}




}
