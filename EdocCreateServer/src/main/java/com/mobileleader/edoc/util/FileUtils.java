package com.mobileleader.edoc.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	
//	/**
//	 * 파일 암호화 여부 확인 & 복호화
//	 * @param 	encFilePath		암호화 파일 경로
//	 * @param 	decFilePath		복호화 후 파일 저장할 경로
//	 * @return	암호화 여부
//	 */
//	public static boolean decrypt(String encFilePath, String decFilePath) throws Exception {
//		
//		if(isEncrpyted(encFilePath)) {
//			if(AESCrypto.getInstance().Decrypt(encFilePath, decFilePath)) {
//				return true;
//			}else {
//				throw new Exception();
//			}
//		}
//		return false;
//	}
//	/**
//	 * 파일 암호화 여부 확인 
//	 * @param	encFilePath		파일 경로
//	 * @return	암호화 여부
//	 * @throws IOException
//	 */
//	public static boolean isEncrpyted(String encFilePath) throws IOException {
//		boolean isEncrypted = false;
//		
//		byte[] encFileArr = new byte[8];
//		
//		FileInputStream fi = new FileInputStream(encFilePath);
//		fi.read(encFileArr);
//		fi.close();
//		
//		String encVerInfo = new String(encFileArr);
//		if(encVerInfo.equals("inziscv2")){
//			isEncrypted = true;
//		}
//		
//		return isEncrypted;
//	}
	

	/**
	 * 디렉토리생성
	 *
	 * @param 	File file 	디렉토리
	 * @return 	파일 생성 여부
	 */
	public static boolean makeFolder(File dirPath) {
		boolean isMake = true;

		if(dirPath.isFile()) {
			dirPath = new File(dirPath.getParent());
		}
		if(!dirPath.exists()) {
			isMake = dirPath.mkdirs();
		}

		return isMake;
	}
	
	/**
	 * 디렉토리생성
	 *
	 * @param String dirPath 	디렉토리경로
	 * @return 파일 생성 여부
	 */
	public static boolean makeFolder(String dirPath) {
		return makeFolder(new File(dirPath));
	}

	/**
	 * MultipartFile 업로드
	 * 
	 * @param String uploadPath		디렉토리 경로
	 * @param String fileName		저장할 파일 이름
	 * @param MultipartFile file	multipart파일
	 * @return 파일 업로드 여부
	 */
    public static boolean uploadFile(String uploadPath, String fileName, MultipartFile file){
    	try {    		
	    	if(!uploadPath.equals("")) {
				makeFolder(uploadPath); 
			}
	
	        File target = new File(uploadPath, fileName);
	        
			file.transferTo(target);
			
			return true;
			
		} catch (IllegalStateException | IOException e) {
			return false;
		}
    }
}
