package com.mobileleader.edoc.util;

public class StringUtils {
	
	/**
	 * nvl
	 *
	 * @param value
	 *            대상문자열
	 * @return 결과문자열
	 */
	public static String nvl(String value) {
		return (value == null ? "" : value.trim());
	}

	/**
	 * nvl
	 *
	 * @param value
	 *            대상문자열
	 * @param dftvalue
	 *            디폴트문자열
	 * @return 결과문자열
	 */
	public static String nvl(String value, String dftvalue) {
		if (value == null || "".equals(value.trim())) {
			return dftvalue;
		} else {
			return value.trim();
		}
	}
	
	/**
	 * cutString
	 *
	 * @param value
	 *            대상문자열
	 * @param size
	 *            결과문자열길이
	 * @return 결과문자열
	 */
	public static String cutString(String value, int size) {

		if (value != null) {
			int len = value.length();
			int cnt = 0;
			int index = 0;
			while ((index < len) && (cnt < size)) {
				if (value.charAt(index++) < 256)
					++cnt;
				else {
					cnt += 2;
				}
			}
			if (index < len) {
				value = value.substring(0, index);
			}
		}
		return value;
	}
	

	/**
	 * value의 왼쪽에서 시작하여 length 만큼의 "결과문자열"을 리턴한다.<br>
	 * value의 길이가 length보다 클 경우 "결과문자열"은 value의 왼쪽에서 시작하여 length 크기 만큼 잘린 것이 된다.<br>
	 * value의 길이가 length보다 작을 경우 "결과문자열"은 value의 오른쪽(끝)에 모자라는 개수만큼 지정된 padValue가
	 * 추가되어 생성된다.
	 *
	 * @param value
	 *            대상문자열
	 * @param padValue
	 *            더해지는문자
	 * @param length
	 *            결과문자열길이
	 * 
	 * @return 결과문자열
	 */
	public static String padLeft(String value, char padValue, int length) {
		if (value == null)
			value = "";

		byte[] orgByte = value.getBytes();
		int orglength = orgByte.length;

		if (orglength < length) // add Padding character
		{
			byte[] paddedBytes = new byte[length];

			int padlength = length - orglength;

			for (int i = 0; i < padlength; i++) {
				paddedBytes[i] = (byte) padValue;
			}

			System.arraycopy(orgByte, 0, paddedBytes, padlength, orglength);

			return new String(paddedBytes);
		} else if (orglength > length) // 주어진 길이보다 남는다면, 주어진 길이만큼만 잘른다.
		{
			byte[] paddedBytes = new byte[length];
			System.arraycopy(orgByte, 0, paddedBytes, 0, length);
			return new String(paddedBytes);
		}

		return new String(orgByte);
	}

	/**
	 * padRight
	 *
	 * @param value
	 *            대상문자열
	 * @param padValue
	 *            더해지는문자
	 * @param length
	 *            결과문자열길이
	 * @return 결과문자열
	 */
	public static String padRight(String value, char padValue, int length) {
		if (value == null)
			value = "";

		byte[] orgByte = value.getBytes();
		int orglength = orgByte.length;

		if (orglength < length) // add Padding character
		{
			byte[] paddedBytes = new byte[length];

			System.arraycopy(orgByte, 0, paddedBytes, 0, orglength);
			while (orglength < length) {
				paddedBytes[orglength++] = (byte) padValue;
			}
			return new String(paddedBytes);
		} else if (orglength > length) // 주어진 길이보다 남는다면, 주어진 길이만큼만 잘른다
		{
			byte[] paddedBytes = new byte[length];
			System.arraycopy(orgByte, 0, paddedBytes, 0, length);
			return new String(paddedBytes);
		}

		return new String(orgByte);
	}

	/**
	 * padRight
	 *
	 * @param value
	 *            대상문자열
	 * @param padValue
	 *            더해지는문자
	 * @param length
	 *            결과문자열길이
	 * @return 결과문자열
	 */
	public static String padRight(String value, char padValue, int length, String charset) throws Exception
	{
		if (value == null)
			value = "";

		byte[] orgByte = value.getBytes(charset);
		int orglength = orgByte.length;

		if (orglength < length) // add Padding character
		{
			byte[] paddedBytes = new byte[length];

			System.arraycopy(orgByte, 0, paddedBytes, 0, orglength);
			while (orglength < length) {
				paddedBytes[orglength++] = (byte) padValue;
			}
			return new String(paddedBytes);
		} else if (orglength > length) // 주어진 길이보다 남는다면, 주어진 길이만큼만 잘른다
		{
			byte[] paddedBytes = new byte[length];
			System.arraycopy(orgByte, 0, paddedBytes, 0, length);
			return new String(paddedBytes);
		}

		return new String(orgByte);
	}
	
	/**
	 * nullToSpace
	 *
	 * @param value
	 *            대상문자열
	 * @return 결과문자열
	 */
	public static String nullToSpace(String value) {
		if (value == null || "".equals(value)) {
			return " ";
		} else {
			return value;
		}
	}
	
	/**
	 * cut Extension
	 * @param value	
	 * 			대상문자열
	 * @return 결과문자열
	 */
	public static String cutExtension(String value) {
		if(value == null || "".equals(value)) {
			return "";
		}

		return value.substring(0, value.lastIndexOf("."));
	}
}
