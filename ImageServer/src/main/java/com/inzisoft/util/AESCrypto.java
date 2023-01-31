package com.inzisoft.util;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inzisoft.crypto.util.base64.Base64;
import com.mobileleader.image.util.CommonUtil;

public class AESCrypto {

	private static boolean loaded = false;
	private static long cryptObj = 0;
	private static final Logger logger = LoggerFactory.getLogger(AESCrypto.class);
	
	public static void init(String cryptoFile, String jniFile, String licenseFile, String keyFile) throws InziCryptoException {
		logger.info("Start init InziCrypto !!");
		// try System.load
		if(jniFile != null && !jniFile.isEmpty()) {
			File candidateFile = new File(jniFile);
			if(candidateFile.exists()) {
				try {
					System.load(jniFile);
					loaded = true;
				} catch(UnsatisfiedLinkError e) {
					logger.error("****** Failed to load JNI : " + e.getMessage());
					throw new InziCryptoException(-99999, "so파일 로딩에 실패하였습니다.");
				} catch(SecurityException e) {
					logger.error("****** Failed to load JNI : " + e.getMessage());
					throw new InziCryptoException(-99999, "so파일 로딩에 실패하였습니다.");
				}
			}
		}
		
		if(loaded == false) {
			try {
				System.loadLibrary("InziiscFileCryptJNI");
				System.loadLibrary("InziiscFileCrypt");
				loaded = true;
			} catch(UnsatisfiedLinkError e) {
				logger.error("Failed to loadLibrary JNI : " + e.getMessage());
				throw new InziCryptoException(-99999, "so파일 로딩에 실패하였습니다.");
			} catch(SecurityException e) {
				logger.error("Failed to loadLibrary JNI : " + e.getMessage());
				throw new InziCryptoException(-99999, "so파일 로딩에 실패하였습니다.");
			}
		}
		
		if(loaded == false)  {
			return;
		}
		
		checkLicenseFromFile(licenseFile);
		
		/*
		 * 가능하면 cryptObj 생성 및 키 설정은 한 번만 하는 것이 바람직 (singleton 패턴 처럼)
		 * 매번 키 설정 하는 경우 빈번한 파일I/O로 인한 성능 하락 및 CPU 점유율 문제 발생 가능 
		 */
		
		if(cryptObj != 0){
			return;
		}
		
		cryptObj = AESCryptoJNI.CreateObj();			
		if (cryptObj == 0) {
			logger.error("Failed to create obj");
			throw new InziCryptoException(99999, "Failed to create obj");
		}
		
		setKeysFromFile(keyFile);
		
		logger.info("Success init InziCrypto !!");
		
		
	}
	
	public static void checkLicenseFromFile(String licenseFile) throws InziCryptoException {
		
		if(loaded == false) {
			throw new InziCryptoException(99999, "JNI File is not loaded!!!");
		}
		
		int ret = CommonJNI.CheckLicenseFromFile(licenseFile);
		if (ret != 0) {
			
			logger.error("Failed to check license, errNo = " + ret);
			switch(ret) {
			case 60001:
				throw new InziCryptoException(ret, "파일 경로가 비어있음");
			case 60002:
				throw new InziCryptoException(ret, "라이센스 파일을 찾을 수 없음");
			case 60003:
				throw new InziCryptoException(ret, "메모리 할당에 실패");
			case 60004:
				throw new InziCryptoException(ret, "파일이 비어 있음");
			case 60005:
				throw new InziCryptoException(ret, "잘못된 라이센스 파일");
			case 60006:
				throw new InziCryptoException(ret, "기한 만료");
			case 60007:
				throw new InziCryptoException(ret, "시스템 정보 가져오기 실패");
			case 60008:
				throw new InziCryptoException(ret, "라이센스 데이터가 비어있음");
			default:
				throw new InziCryptoException(99999, "Unknown Error in CommonJNI.CheckLicenseFromFile");
			}
		}
	}
	
	public static void setKeysFromFile(String keyFile) throws InziCryptoException {
		
		if(cryptObj == 0) {
			throw new InziCryptoException(1, "Not Initialized!!");
		}
		
		boolean result = AESCryptoJNI.SetKeysFromFile(cryptObj, keyFile);
		if (result == false) {
			checkLastError("Failed to set keys");
		}
	}

	public static void destroy() {
		
		if(cryptObj != 0) {
			AESCryptoJNI.DestroyObj(cryptObj);
			cryptObj = 0;
		}
	}

	public static int GetErrNo()
	{
	    return AESCryptoJNI.GetErrNo(cryptObj);
	}
	

		
	/**
	 * AES 암/복호화시 체이닝 모드를 설정
	 * 
	 * @param chainingOperMode		[in] 블럭 암호화 방식<br/>
	 * 										0: ECB(기본값), 1: CBC, 2: CFB<br/>
	 * 										암/복화시 서로 다른 체이닝 모드를 설정할 경우 복화화시 원본 데이터가 추출이 되지 않습니다.
	 */
	public static void SetChainingOperMode(int chainingOperMode) {
		
		if(chainingOperMode>=0 && chainingOperMode<=2)
			AESCryptoJNI.SetChainingOperMode(cryptObj, chainingOperMode);
	}		
	
	private static void checkLastError(String message) throws InziCryptoException {
		
		if(cryptObj == 0) {
			throw new InziCryptoException(1, "Not Initialized!!");
		}
		
		int errNo = AESCryptoJNI.GetErrNo(cryptObj);
		logger.info(message +  ", errNo = " + errNo);
		switch(errNo) {
		case 0:
			// Success
			break;
		case -2:
			throw new InziCryptoException(errNo, "암호화된 데이터를 암호화하려고 합니다. 기존 암호화 데이터에 다시 암호화를 하시려면 allowMultipleEncryption값을 1로 넘겨주십시오.");
		case -3:
			throw new InziCryptoException(errNo, "암호화되지 않은 데이터를 복호화하려고 합니다.");
		case -4:
			throw new InziCryptoException(errNo, "키 값이 설정되지 않았습니다. 내부키/외부키/랜덤키가 모두 설정되었는지 확인해 보시기 바랍니다.");
		case -5:
			throw new InziCryptoException(errNo, "원본 데이터가 NULL입니다. 원본 데이터가 유효한 내용인지 확인해 보시기 바랍니다.");
		case -6:
			throw new InziCryptoException(errNo, "원본 데이터의 크기가 0입니다. 내용이 없는 데이터는 암/복호화할 수 없습니다.");
		case -7:
			throw new InziCryptoException(errNo, "결과 데이터를 저장할 메모리가 할당되어 있지 않습니다. 결과 데이터를 저장할 메모리를 할당하셨는지 확인해 보시기 바랍니다.");
		case -9:
			throw new InziCryptoException(errNo, "파일을 열 수 없습니다. 해당 파일이 사용중인지 또는 디스크 여유공간이 충분한지 확인해 보시기 바랍니다.");
		case -10:
			throw new InziCryptoException(errNo, "메모리 할당을 할 수 없습니다. 메모리의 여유공간이 충분한지 확인해 보시기 바랍니다.");
		case -11:
			throw new InziCryptoException(errNo, "암호화 데이터의 길이가 유효하지 않습니다. 암호화 데이터가 손상된 것으로 보입니다.");
		case -12:
			throw new InziCryptoException(errNo, "암호화 데이터 내용이 유효하지 않습니다. 암호화 데이터가 손상된것으로 보입니다.");
		case -13:
			throw new InziCryptoException(errNo, "파일 이름이 없습니다. 파일 이름을 확인해 보시기 바랍니다.");
		case -14:
			throw new InziCryptoException(errNo, "AES 암호화 모듈을 초기화 할 수 없습니다. 설정값이 유효한지 확인해 보시기 바랍니다.");
		case -15:
			throw new InziCryptoException(errNo, "AES 암호화를 할 수 없습니다.");
		case -16:
			throw new InziCryptoException(errNo, "AES 복호화를 할 수 없습니다.");
		case -17:
			throw new InziCryptoException(errNo, "복호화한 데이터가 원본 데이터와 일치하지 않습니다. 암호화시의 키와 복호화시의 키가 다르거나 암호화 데이터가 손상되었습니다.");
		case -18:
			throw new InziCryptoException(errNo, "잘못된 태그 정보가 있습니다. 암호화 데이터가 손상되었거나 최신 모듈로 업데이트가 필요합니다.");
		case -19:
			throw new InziCryptoException(errNo, "암복호화 시 키 정보가 일치하지 않습니다.");
		case -9999:
			throw new InziCryptoException(errNo, "암복호화 오브젝트가 없습니다. 암복호화 오브젝트를 생성하셨는지 확인해 보시기 바랍니다.");
		case 50001:
			throw new InziCryptoException(errNo, "파일 경로가 NULL입니다. 파일 경로를 확인해 보시기 바랍니다.");
		case 50002:
			throw new InziCryptoException(errNo, "키 파일 열기에 실패했습니다. 파일 경로를 확인해 보시기 바랍니다.");
		case 50003:
			throw new InziCryptoException(errNo, "키 파일의 헤더 정보가 올바르지 않습니다. 파일이 손상되었는지 확인해 보시기 바랍니다.");
		case 50004:
			throw new InziCryptoException(errNo, "키 파일의 데이터 정보가 올바르지 않습니다. 파일이 손상되었는지 확인해 보시기 바랍니다.");
		case 50005:
			throw new InziCryptoException(errNo, "키 파일 처리도중 메모리 할당에 실패하였습니다. 메모리의 여유공간이 충분한지 확인해 보시기 바랍니다.");
		case 50006:
			throw new InziCryptoException(errNo, "키 정보가 없습니다. 파일이 손상되었는지 확인해 보시기 바랍니다.");
		case 50007:
			throw new InziCryptoException(errNo, "암복호화 모듈 초기화에 실패하였습니다.");
		default:
			throw new InziCryptoException(errNo, "Unknown");			
		}
	}
	
	
	/*
	 * 멀티쓰레드 사용 시 각 쓰레드의 암호화 혹은 복호화 결과 파일명이 동일한 경우 동시 파일 접근 문제로 system crash 발생할 가능성 있음
	 * 반드시 멀티쓰레드 사용 시 결과 암호화, 복호화 파일은 쓰레드 별로 다르게 생성되어야 함
	 */
	
	
	// 파일 암호화
	public static void encryptFile(String oriFileName, String encFileName) throws InziCryptoException {
		
		boolean result = AESCryptoJNI.Encrypt(cryptObj, oriFileName, encFileName, false); 
		if (result == false) {
			checkLastError("Failed to encrypt");
		}
	}
 
	// 파일 복호화
	static public void decryptFile(String encFileName, String decFileName) throws InziCryptoException {
		boolean effect = AESCryptoJNI.Decrypt(cryptObj, encFileName, decFileName);
		if (!effect) {
			checkLastError("Failed to encrypt File");
		}
	}
	
	// 파일 암호화 (메모리)
	public static byte[] encryptFileMem(byte[] ori) throws InziCryptoException{
		
		int i = AESCryptoJNI.GetEncryptDataSize(cryptObj, ori);
		if (i ==0)
			return null;
		
		byte[] enc = new byte[i];
		boolean ret = AESCryptoJNI.Encrypt(cryptObj, ori, enc, false);
		return enc;
	}

	// 파일 복호화 (메모리)
	public static byte[] deryptFileMem(byte[] ori) throws InziCryptoException {

		// 암호화 여부 확인  안되네~
		//int majorVer = -1;
		//int minorVer = -1;
		//boolean check = AESCryptoJNI.GetEncVer(cryptObj, ori, majorVer, minorVer);
		
		int i = AESCryptoJNI.GetDecryptDataSize(cryptObj, ori);
		if (i == 0) {
			return null;
		}
		byte[] arrayOfByte = new byte[i];
		
		boolean result = AESCryptoJNI.Decrypt(cryptObj,  ori, arrayOfByte); 
		if (result == false) {
			checkLastError("Failed to encrypt");
		}
				
		return arrayOfByte;
	}



	// 문자 암호화
	public static byte[] Encrypt(byte[] oriData, boolean allowMultipleEncryption) {
	    int encDataSize = AESCryptoJNI.GetEncryptDataSize(cryptObj, oriData);
	    if (encDataSize == 0) {
	      return null;
	    }
	
		byte[] encryptData = new byte[encDataSize];
	
		if (AESCryptoJNI.Encrypt(cryptObj, oriData, encryptData, allowMultipleEncryption) == true) {
		  return encryptData;
		}
		return null;
	}
	
	// 문자 복호화
	public static byte[] Decrypt(byte[] paramArrayOfByte) throws InziCryptoException  {
		
		int i = AESCryptoJNI.GetDecryptDataSize(cryptObj, paramArrayOfByte);
		if (i == 0) {
			return null;
		}
		byte[] arrayOfByte = new byte[i];
		if (AESCryptoJNI.Decrypt(cryptObj, paramArrayOfByte, arrayOfByte)) {
			return arrayOfByte;
		}
		checkLastError("Failed to decrypt");
		return null;
	}
	
	
	// 문자 암호화 + base64
	public static String EncryptWithBase64(String oriData) {
		byte[] enc = AESCrypto.Encrypt(oriData.getBytes(), false);
		return Base64.encodeBase64String(enc);
	}

	// base64 +  문자 복호화  
	public static String DecryptWithBase64(String oriData) throws InziCryptoException {
		
		String ret = "";
		
		if (CommonUtil.isBase64Encoding(oriData)) {
			logger.debug("This string is base64.");
			byte[] tmpByteArr_enc_base64 = Base64.decodeBase64(oriData);
			
			if (tmpByteArr_enc_base64 != null) 
				logger.debug("base64 byte length is " + new String(tmpByteArr_enc_base64).length());
			else
				logger.debug("base64 byte is null.");
			 
			
			byte[] tmpByteArr_dec = AESCrypto.Decrypt(tmpByteArr_enc_base64);
			if (tmpByteArr_dec != null)
				logger.debug("aesScrypto byte is " + new String(tmpByteArr_enc_base64).length());
			else
				logger.debug("aesScrypto byte is null.");
			
			if (tmpByteArr_dec==null) {
				logger.debug("aesScrypto decoding is faild, because error code is " + AESCrypto.GetErrNo());
				// 암호화 되지 않은 데이터가 들어올 경우 전송된 데이터를 그대로 보낸다.
				if (AESCrypto.GetErrNo() == -3) {
					ret = oriData;
				}
			}else {						
				ret = new String(tmpByteArr_dec);												
			}
		} else {
			logger.debug("base64 is not configured.");
		}
	
		return ret;
	}
}

 