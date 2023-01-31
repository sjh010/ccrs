/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inzisoft.util;

public class AESCryptoJNI
{
	/**
	 * 암복호화 객체 생성 
	 * 
	 * @return 암복호화 객체 포인터 : 성공<br/>
	 * 		   0 : 실패
	 */
	protected static native long CreateObj();
	
	/**
	 * 암복호화 객체 해제
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 */
	protected static native void DestroyObj(long inziAES);
	
	/**
	 * 내부키 설정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param internalKey			[in] 내부키 
	 */
	protected static native void SetInternalKey(long inziAES, String internalKey);
	
	/**
	 * 외부키 설정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param externalKey			[in] 외부키 
	 */
	protected static native void SetExternalKey(long inziAES, String externalKey);
	
	/**
	 * 랜덤키 설정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param randomKey				[in] 랜덤키 
	 */
	protected static native void SetRandomKey(long inziAES, String randomKey);
	
	/**
	 * 키 파일을 통해 내부키/외부키/랜덤키를 설정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param path					[in] 키 파일의 경로 
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인 
	 */
	protected static native boolean SetKeysFromFile(long inziAES, String path);
	
	/**
	 * 키 파일을 메모리 방식으로 내부키/외부키/랜덤키를 설정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param data					[in] 키 메모리 데이터
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인 
	 */
	protected static native boolean SetKeysFromMemory(long inziAES, byte[] data);
	
	/**
	 * AES 암/복호화시 블럭 크기를 지정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param blockSize				[in] 블럭 크기<br/>
	 * 										16(128bit), 24(192bit), 32(256bit, 기본값)<br/>
	 * 										블럭크기에 따라 암호화 비트수가 결정됩니다.<br/>
	 * 										암/복화시 서로 다른 블럭 크기를 설정할 경우 복화화시 원본 데이터가 추출이 되지 않습니다.
	 */
	protected static native void SetBlockSize(long inziAES, int blockSize);
	
	/**
	 * AES 암/복호화시 패딩되는 데이터를 어떻게 채울지 설정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param blockPaddingMode		[in] 블럭 패딩 모드<br/>
	 * 										0: ZEROS(기본값), 1: BLANKS, 2: PKCS7<br/>
	 * 										암/복화시 서로 다른 패딩 방식을 설정할 경우 복화화시 원본 데이터가 추출이 되지 않습니다.
	 */
	protected static native void SetBlockPaddingMode(long inziAES, int blockPaddingMode);
	
	/**
	 * AES 암/복호화시 체이닝 모드를 설정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param chainingOperMode		[in] 블럭 암호화 방식<br/>
	 * 										0: ECB(기본값), 1: CBC, 2: CFB<br/>
	 * 										암/복화시 서로 다른 체이닝 모드를 설정할 경우 복화화시 원본 데이터가 추출이 되지 않습니다.
	 */
	protected static native void SetChainingOperMode(long inziAES, int chainingOperMode);
	
	/**
	 * AES 암/복호화시 태그 처리 방식 설정
	 * 
	 * @param inziAES				[in] 암복호화 객체 포인터	
	 * @param tagProcessingMode		[in] 태그 처리 방식<br/>
	 * 										0: 현재 버전에서 지원되지 않는 tag 에러 처리(기본값)<br/>
	 * 										1: 현재 버전에서 지원되지 않는 tag가 필수 tag인 경우 에러 처리, 그렇지 않은 경우 skip 처리
	 */
	protected static native void SetTagProcessingMode(long inziAES, int tagProcessingMode);
	
	/**
	 * 메모리에 있는 원본 데이터를 할당된 메모리로 암호화를 수행(memory -> memory)
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param oriData					[in] 원본 데이터 
	 * @param encryptData				[in] 암호화 데이터
	 * @param allowMultipleEncryption	[in] 원본 데이터가 암호화되어 있는 경우의 암호화 수행여부<br/>
	 * 											true: 암호화 수행, false: 암호화를 수행하지 않음
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean Encrypt(long inziAES, byte[] oriData, byte[] encryptData, boolean allowMultipleEncryption); //Added
	
	/**
	 * 파일에 있는 원본 데이터를 할당된 메모리로 암호화를 수행(file -> memory)
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param oriFileName				[in] 원본 데이터 파일 경로
	 * @param encryptData				[in] 암호화 데이터
	 * @param allowMultipleEncryption	[in] 원본 데이터가 암호화되어 있는 경우의 암호화 수행여부<br/>
	 * 											true: 암호화 수행, false: 암호화를 수행하지 않음
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean Encrypt(long inziAES, String oriFileName, byte[] encryptData, boolean allowMultipleEncryption); //Added
	
	/**
	 * 메모리에 있는 원본 데이터를 암호화하여 파일로 저장(memory -> file)
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param oriData					[in] 원본 데이터
	 * @param encFileName				[in] 암호화 데이터를 저장할 파일 경로
	 * @param allowMultipleEncryption	[in] 원본 데이터가 암호화되어 있는 경우의 암호화 수행여부<br/>
	 * 											true: 암호화 수행, false: 암호화를 수행하지 않음
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean Encrypt(long inziAES, byte[] oriData, String encFileName, boolean allowMultipleEncryption); //Added
	
	/**
	 * 파일에 있는 원본 데이터를 암호화하여 파일로 저장(file -> file)
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param oriFileName				[in] 원본 데이터 파일 경로
	 * @param encFileName				[in] 암호화 데이터를 저장할 파일 경로
	 * @param allowMultipleEncryption	[in] 원본 데이터가 암호화되어 있는 경우의 암호화 수행여부<br/>
	 * 											true: 암호화 수행, false: 암호화를 수행하지 않음
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean Encrypt(long inziAES, String oriFileName, String encFileName, boolean allowMultipleEncryption);
	
	/**
	 * memory -> memory로 암호화 하는 경우 암호화 데이터의 크기 가져오기
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param oriData					[in] 원본 데이터	 
	 * @return 양수 : 암호화 데이터 크기<br/>
	 * 		   0 : 암호화 데이터 크기를 구할 수 없음, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native int GetEncryptDataSize(long inziAES, byte[] oriData); //Added
	
	/**
	 * file -> memory로 암호화 하는 경우 암호화 데이터의 크기 가져오기
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param oriFileName				[in] 원본 테이터 파일 경로 
	 * @return 양수 : 암호화 데이터 크기<br/>
	 * 		   0 : 암호화 데이터 크기를 구할 수 없음, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native int GetEncryptDataSize(long inziAES, String oriFileName);
	
	/**
	 * 메모리에 있는 암호화 데이터를 할당된 메모리로 복호화를 수행(memory -> memory)
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param encData					[in] 암호화 데이터 
	 * @param decryptData				[in] 복호화 데이터 
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean Decrypt(long inziAES, byte[] encData, byte[] decryptData); //Added
	
	/**
	 * 파일에 있는 암호화 데이터를 할당된 메모리로 복호화를 수행(file -> memory)
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param encFileName				[in] 암호화 데이터 파일 경로
	 * @param decryptData				[in] 복호화 데이터
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean Decrypt(long inziAES, String encFileName, byte[] decryptData); //Added
	
	/**
	 * 메모리에 있는 암호화 데이터를 복호화하여 파일로 저장(memory -> file)
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param encData					[in] 암호화 데이터
	 * @param decFileName				[in] 복호화 데이터를 저장할 파일 경로
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean Decrypt(long inziAES, byte[] encData, String decFileName); //Added
	
	/**
	 * 파일에 있는 암호화 데이터를 복호화하여 파일로 저장(file -> file)
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param encFileName				[in] 암호화 데이터 파일 경로
	 * @param decFileName				[in] 복호화 데이터를 저장할 파일 경로
	 * @return 1 : 성공<br/>
	 * 		       그외 : 실패, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean Decrypt(long inziAES, String encFileName, String decFileName);
	
	/**
	 * memory -> memory로 복호화 하는 경우 복호화 데이터의 크기 가져오기
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param encData					[in] 암호화 데이터	 
	 * @return 양수 : 복호화 데이터 크기<br/>
	 * 		   0 : 복호화 데이터 크기를 구할 수 없음, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native int GetDecryptDataSize(long inziAES, byte[] encData); //Added
	
	/**
	 * file -> memory로 복호화 하는 경우 복호화 데이터의 크기 가져오기
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param encFileName				[in] 암호화 테이터 파일 경로 
	 * @return 양수 : 복호화 데이터 크기<br/>
	 * 		   0 : 복호화 데이터 크기를 구할 수 없음, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native int GetDecryptDataSize(long inziAES, String encFileName);
	
	/**
	 * 데이터의 암복호화 여부 확인 및 암호화 데이터인 경우 암호화 버전 가져오기
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param encData					[in] 암호화 데이터
	 * @param majorVer					[in] 메이저 버전 번호
	 * @param minorVer					[in] 마이너 버전 번호
	 * @return 1 : 암호화 데이터<br/>
	 * 		   0 : 암호화 데이터가 아니거나 확인 할 수 없음, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean GetEncVer(long inziAES, byte[] encData, int majorVer, int minorVer); //Added
	
	/**
	 * 파일의 암복호화 여부 확인 및 암호화 파일인 경우 암호화 버전 가져오기
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @param encFileName				[in] 암호화 데이터 파일 경로
	 * @param majorVer					[in] 메이저 버전 번호
	 * @param minorVer					[in] 마이너 버전 번호
	 * @return 1 : 암호화 데이터<br/>
	 * 		   0 : 암호화 데이터가 아니거나 확인 할 수 없음, 자세한 에러는 GetErrNo를 통해서 확인
	 */
	protected static native boolean GetEncVer(long inziAES, String encFileName, int[] major, int[] minorVer);
	
	/**
	 * 암복호화 실패 시 상세 에러코드 가져오기
	 * 
	 * @param inziAES					[in] 암복호화 객체 포인터	
	 * @return 	0 함수가 성공적으로 수행되었습니다.<br/>
	 * 			-2 암호화된 데이터를 암호화하려고 합니다. 기존 암호화 데이터에 다시 암호화를 하시려면 allowMultipleEncryption값을 1로 넘겨주십시오.<br/>
	 * 			-3 암호화되지 않은 데이터를 복호화하려고 합니다.<br/>
	 * 			-4 키 값이 설정되지 않았습니다. 내부키/외부키/랜덤키가 모두 설정되었는지 확인해 보시기 바랍니다.<br/>
	 * 			-5 원본 데이터가 NULL입니다. 원본 데이터가 유효한 내용인지 확인해 보시기 바랍니다.<br/>
	 * 			-6 원본 데이터의 크기가 0입니다. 내용이 없는 데이터는 암/복호화할 수 없습니다.<br/>
	 * 			-7 결과 데이터를 저장할 메모리가 할당되어 있지 않습니다. 결과 데이터를 저장할 메모리를 할당하셨는지 확인해 보시기 바랍니다.<br/>
	 * 			-9 파일을 열 수 없습니다. 해당 파일이 사용중인지 또는 디스크 여유공간이 충분한지 확인해 보시기 바랍니다.<br/>
	 * 			-10 메모리 할당을 할 수 없습니다. 메모리의 여유공간이 충분한지 확인해 보시기 바랍니다.<br/>
	 * 			-11 암호화 데이터의 길이가 유효하지 않습니다. 암호화 데이터가 손상된 것으로 보입니다.<br/>
	 * 			-12 암호화 데이터 내용이 유효하지 않습니다. 암호화 데이터가 손상된것으로 보입니다.<br/>
	 * 			-13 파일 이름이 없습니다. 파일 이름을 확인해 보시기 바랍니다.<br/>
	 * 			-14 AES 암호화 모듈을 초기화 할 수 없습니다. 설정값이 유효한지 확인해 보시기 바랍니다.<br/>
	 * 			-15 AES 암호화를 할 수 없습니다.<br/>
	 * 			-16 AES 복호화를 할 수 없습니다.<br/>
	 * 			-17 복호화한 데이터가 원본 데이터와 일치하지 않습니다. 암호화시의 키와 복호화시의 키가 다르거나 암호화 데이터가 손상되었습니다.<br/>
	 *			-18 잘못된 태그 정보가 있습니다. 암호화 데이터가 손상되었거나 최신 모듈로 업데이트가 필요합니다.<br/>
	 *			-19 암복호화 시 키 정보가 일치하지 않습니다.<br/>
	 * 			-9999 암복호화 오브젝트가 없습니다. 암복호화 오브젝트를 생성하셨는지 확인해 보시기 바랍니다.<br/>
	 * 			50001 파일 경로가 NULL입니다. 파일 경로를 확인해 보시기 바랍니다.<br/>
	 * 			50002 키 파일 열기에 실패했습니다. 파일 경로를 확인해 보시기 바랍니다.<br/>
	 * 			50003 키 파일의 헤더 정보가 올바르지 않습니다. 파일이 손상되었는지 확인해 보시기 바랍니다.<br/>
	 * 			50004 키 파일의 데이터 정보가 올바르지 않습니다. 파일이 손상되었는지 확인해 보시기 바랍니다.<br/>
	 * 			50005 키 파일 처리도중 메모리 할당에 실패하였습니다. 메모리의 여유공간이 충분한지 확인해 보시기 바랍니다.<br/>
	 * 			50006 키 정보가 없습니다. 파일이 손상되었는지 확인해 보시기 바랍니다.<br/>
	 * 			50007 암복호화 모듈 초기화에 실패하였습니다.<br/>
	 * 			50008 잘못된 키 파일입니다. 파일이 손상되었는지 확인해 보시기 바랍니다.
	 */
	protected static native int GetErrNo(long inziAES);
	static
	{
//		System.loadLibrary("InziiscFileCrypt");
//		System.loadLibrary("InziiscFileCryptJNI");
//		System.load("D:/Projects/11.ccrs/Sources/crypto_module/module/InziiscFileCrypt2.dll");
//		System.load("D:/Projects/11.ccrs/Sources/crypto_module/module/InziiscFileCryptJNI.dll");
		System.out.println("### loadLibrary success..(AESCryptoJNI)");
	}
}
