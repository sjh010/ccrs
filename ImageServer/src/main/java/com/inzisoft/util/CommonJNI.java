//======================================================
/* Copyright (c) 1999-2015 INZISOFT Co., Ltd. All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited 
 * All information contained herein is, and remains the property of INZISOFT Co., Ltd.
 * The intellectual and technical concepts contained herein are proprietary to INZISOFT Co., Ltd.
 * Dissemination of this information or reproduction of this material is 
 * strictly forbidden unless prior written permission is obtained from INZISOFT Co., Ltd.
 * Proprietary and Confidential.
 */
//======================================================

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inzisoft.util;
/**
 *
 * @author majorofc
 */
public class CommonJNI
{
	/**
	 * 서버 라이센스 체크
	 * 
	 * @param path				[in] 서버 라이센스 파일 경로
	 * @return 0: 성공<br/>
	 * 			 그외: 실패<br/>
	 * 			에러 목록<br/>
	 * 			60001 - 파일 경로가 비어있음<br/>
	 * 			60002 - 라이센스 파일을 찾을 수 없음<br/>
	 * 			60003 - 메모리 할당에 실패<br/>
	 * 			60004 - 파일이 비어 있음<br/>
	 * 			60005 - 잘못된 라이센스 파일<br/>
	 * 			60006 - 기한 만료<br/>
	 * 			60007 - 시스템 정보 가져오기 실패<br/>  
	 * 			60008 - 라이센스 데이터가 비어있음<br/>
	 * 			60009 - cpu 정보 가져오기 실패
	 */
	public static native int CheckLicenseFromFile(String path);
	
	/**
	 * 안드로이드 라이센스 체크
	 * 
	 * @param context			[in]  java context
	 * @return ml_check_packagename의 return 값과 동일
	 */
	public static native int CheckLicenseForAndroid(Object context);
	
	/**
	 * 암복호화 모듈 버전 가져오기 API
	 * 
	 * @return 버전 정보(x.x.x.x)
	 */	
	public static native String GetCryptVersion();
	
	static
	{
//		System.loadLibrary("InziiscFileCrypt");
//		System.loadLibrary("InziiscFileCryptJNI");
		
//		System.load("D:\\Projects\\11.ccrs\\Sources\\crypto_module\\module\\InziiscFileCrypt2.dll");
//		System.load("D:\\Projects\\11.ccrs\\Sources\\crypto_module\\module\\InziiscFileCryptJNI.dll");
	}
}
