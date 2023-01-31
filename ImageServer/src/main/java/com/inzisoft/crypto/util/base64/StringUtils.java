package com.inzisoft.crypto.util.base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

	private static byte[] getBytes(String string, Charset charset) {
		if (string == null) {
			return null;
		}
		return string.getBytes(charset);
	}

	public static byte[] getBytesUtf8(String string) {
		return getBytes(string, Charset.forName("UTF-8"));
	}

	private static String newString(byte[] bytes, Charset charset) {
		return bytes == null ? null : new String(bytes, charset);
	}

	public static String newStringUtf8(byte[] bytes) {
		return newString(bytes, Charset.forName("UTF-8"));
	}

	/**
	 * length 보다 작은 경우 왼쪽에 pad 해준다.
	 * 
	 * @param str
	 * @param length
	 * @param pad
	 * @return
	 */
	public static String lPad(String str, int length, char pad){
		String value = (str == null || str == "null" ? "" : str);
		return String.format("%"+length+"s", value).replace(' ', pad);
	}
	
	/**
	 * length 보다 작은 경우 오른쪽에 pad 해준다. 
	 * @param str
	 * @param length
	 * @param pad
	 * @return
	 */
	public static String rPad(String str, int length, char pad){
		String value = (str == null || str == "null" ? "" : str);
		return String.format("%-"+length+"s", value).replace(' ', pad);
	}
	
	/**
	 * 문자열을 EUC-KR로 길이를 구한다음에 패딩을 한다. 
	 * @param str
	 * @param length
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String RPad(String str, int length, String pad) throws UnsupportedEncodingException{
		int len;
		len = str.getBytes("EUC-KR").length;
		if( len >= length)
			return str;
		else{
			for( int i = 0; i < length - len; i++){
				str += pad;
			}
		}
		
		return str;
	}
	
	/**
	 * inziCrypto 복호화
	 * 
	 * @param encryptedStr
	 * @return
	 */
//	public static String inziCryptoDecrypt(String encryptedStr) throws NhimException {
//		String decryptedStr = null;
//		
//		if (encryptedStr != null && !encryptedStr.equals("") && !encryptedStr.equals("(null)")) {
//			try {
//				logger.debug("Try decrypt : "+ encryptedStr);
//				byte[] bytes = Base64.decodeBase64(encryptedStr.replace(' ', '+'));
//				byte[] result = AESCrypto.decrypt(bytes);
//				decryptedStr = new String(result, "UTF-8");
//			} catch (Exception e) {
//				logger.error("문자열 복호화 실패");
//				throw new NhimException(-1, "문자열 복호화를 실패하였습니다.");
//			}
//			//logger.debug("decryptedStr="+decryptedStr);
//		}
//		
//		return decryptedStr;
//	}	
	
	/**
	 * 숫자인지 체크
	 * @param input
	 * @return
	 */
	public static boolean isNumeric(String input){
		if(Pattern.matches("^[0-9]+$", input))
			return true;
		else
			return false;
	}
	
	/**
	 * UUID 파일명 구하기
	 * @param fileName
	 * @return
	 */
	public static String getUUIDFileName(String orgFileName){
		logger.debug("Original File name : "  + orgFileName);		
		return UUID.randomUUID().toString()+".tif";
	}
}
