package com.inzisoft.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.inzisoft.crypto.util.base64.Base64;

/**
 * 인지 암복호화 테스트
 * 윈도우에서 테스트시 시스템 환경설정 필수.
 * 
 * @author user
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:properties/crypto-properties.xml")
public class AESCryptoTest {	
	
	
//	@Autowired
//	CryptoProperties cryptoProperties;
	 
	
	@Before
	public void cryptoInit() {
		
//		String s = cryptoProperties.getJniFilePath();
		
		String licenseFile = "D:/Projects/11.ccrs/Sources/crypto_module/module/iisClient.dat";
		String keyFile = "D:/Projects/11.ccrs/Sources/crypto_module/module/inzi_enc_ccrs_bpr.dat";
		String jniFile = "D:/Projects/11.ccrs/Sources/crypto_module/module/InziiscFileCryptJNI.dll";
		String cryptoFile = "D:/Projects/11.ccrs/Sources/crypto_module/module/InziiscFileCrypt2.dll";
				
		try {			
			AESCrypto.init(cryptoFile, jniFile, licenseFile, keyFile);
		} catch (InziCryptoException e) {
			e.printStackTrace();
		}
	}
	
	@Test	// 인지암복호화
	public void cryptoTest() {
 
		String str = "페이퍼리스ABC123";
		byte[] enc = AESCrypto.Encrypt(str.getBytes(), false);
		String encStr = new String(enc);
		System.out.println(encStr);
		
		try {
			byte[] dec = AESCrypto.Decrypt(enc);
			String decStr = new String(dec);
			System.out.println(decStr);
			assertEquals(str, decStr);
		} catch (InziCryptoException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test	// 인지암복호화 + base64
	public void cryptoWithBase64Test() {
 
		String str = "페이퍼리스ABC123";
		byte[] enc = AESCrypto.Encrypt(str.getBytes(), false);
		byte[] base64 = Base64.encodeBase64(enc);
		String encStr = new String(base64);
		System.out.println(encStr);
		
		try {
			byte[] decBase64 = Base64.decodeBase64(encStr);
			byte[] dec = AESCrypto.Decrypt(decBase64);
			String decStr = new String(dec);
			System.out.println(decStr);
			assertEquals(str, decStr);
		} catch (InziCryptoException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test	// 인지암복호화 + base64
	public void cryptoWithBase64Test2() {
 
		String str = "페이퍼리스ABC123";
		byte[] enc = AESCrypto.Encrypt(str.getBytes(), false);
		String encStr = Base64.encodeBase64URLSafeString(enc);
		//String encStr = new String(base64);
		System.out.println(encStr);
		
		try {
			byte[] decBase64 = Base64.decodeBase64(encStr);
			byte[] dec = AESCrypto.Decrypt(decBase64);
			String decStr = new String(dec);
			System.out.println(decStr);
			assertEquals(str, decStr);
		} catch (InziCryptoException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test // 파일 암복호화
	public void crtyptoFile() {
		
		String ori = "d:/ori.jpg";
		String enc = "d:/enc.jpg";
		String dec = "d:/dec.jpg";
		
		try {
			// 파일 암호화
			AESCrypto.encryptFile(ori, enc);
			
			// 파일 복호화
			AESCrypto.decryptFile(enc, dec);
		} catch (InziCryptoException e) {
			e.printStackTrace();
		}
	}
	
	
}
