package com.mobileleader.edoc.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 서버 공통 Property
 * 참고) service.properties
 * 
 * @author yh.kim
 *
 */
@Repository
public class EdocServiceProp {
	
	/*
	 * 테스트서버와 운영서버를 구분하기 위한 값 
	 */
	private static final String TEST_PROFILE_KEY = "9";			// 로컬 & 테스트서버 키값
	private static final String PROD_PROFILE_KEY = "1";			// 운영서버 키값
	
	/*
	 * 운영서버 profile 설정값
	 */
	private static final String PROD_PROFILE_NAME = "prd";
	
	
	/*
	 * 서버 profile
	 */
	private static String profile;
	
	@Value("#{inziProperty['server.profile']}")
	private void setProfile(String _profile) {
		profile = _profile;
	}
	
	public static String getProfile() {
		return profile;
	}
	
	public static String getProfileKey() {
		if(profile.startsWith(PROD_PROFILE_NAME)) {
			return PROD_PROFILE_KEY;
		}
		return TEST_PROFILE_KEY;
	}

	/*
	 * 전자문서 생성에 필요한 파일들이 저장되는 루트 경로(업로드된 파일 저장하는 루트)
	 * ex) /programs/data/edoc/
	 * 
	 * 주의! 마지막에 separator('/')포함.
	 */
	private static String DATA_HOME_PATH;
	
	@Value("#{inziProperty['path.home.data.edoc']}")
	private void setDataHomePath(String path) {
		DATA_HOME_PATH = path;
	}
	
	public static String DATA_HOME_PATH() {
		return DATA_HOME_PATH;
	}
	
	/*
	 * 개발기간 중 업로드한 전자서식 파일이 저장되어있는 루트 경로
	 * ex) /programs/data/eform/
	 * 
	 * 주의! 마지막에 separator('/')포함.
	 */
	private static String EFORM_ROOT_PATH;
	
	@Value("#{inziProperty['path.home.data.eform']}")
	private void setEformRootPath(String path) {
		EFORM_ROOT_PATH = path;
	}
	
	public static String EFORM_ROOT_PATH() {
		return EFORM_ROOT_PATH;
	}
	
	/*
	 * 전자문서 생성 요청 시, 업로드 된 전자문서용 ZIP 파일을 압축 해제하여 저장하는 폴더  명
	 * ex) xml/
	 * 
	 * 폴더 생성 경로 : $(DATA_HOME_PATH)/[yyyyMMdd]/[부점코드]/[전자문서키]/xml/
	 */
	private static String XML_FILE_PATH;
	
	@Value("#{inziProperty['path.file.xml']}")
	private void setXmlFilePath(String path) {
		XML_FILE_PATH = path;
	}
	
	public static String XML_FILE_PATH() {
		return XML_FILE_PATH;
	}

	/*
	 * 전자문서 생성 결과 PDF 파일이 저장되는 폴더 명
	 * ex) pdf/
	 * 
	 * 폴더 생성 경로 : $(DATA_HOME_PATH)/[yyyyMMdd]/[부점코드]/[전자문서키]/pdf/
	 */
	private static String PDF_FILE_PATH;
	
	@Value("#{inziProperty['path.file.pdf']}")
	private void setPdfFilePath(String path) {
		PDF_FILE_PATH = path;
	}
	
	public static String PDF_FILE_PATH() {
		return PDF_FILE_PATH;
	}
	
	/*
	 * 임시저장 요청 시, 업로드 된 ZIP 파일이 저장되는 폴더 명
	 * ex) tmpr/
	 * 
	 * 폴더 생성 경로 : $(DATA_HOME_PATH)/[yyyyMMdd]/[부점코드]/[전자문서키]/tmpr/
	 */
	private static String TMPR_FILE_PATH;
	
	@Value("#{inziProperty['path.file.tmpr']}")
	private void setTmprFilePath(String path) {
		TMPR_FILE_PATH = path;
	}
	
	public static String TMPR_FILE_PATH() {
		return TMPR_FILE_PATH;
	}
	
	/*
	 * 전자서식 form파일 저장 폴더
	 */
	private static String FORM_FILE_PATH;
	
	@Value("#{inziProperty['path.file.form']}")
	private void setFormFilePath(String path) {
		FORM_FILE_PATH = path;
	}
	
	public static String FORM_FILE_PATH() {
		return FORM_FILE_PATH;
	}
	
	/*
	 * 전자서식 비즈니스 로직 저장 폴더
	 */
	private static String BIZ_FILE_PATH;
	
	@Value("#{inziProperty['path.file.biz']}")
	private void setBizFilePath(String path) {
		BIZ_FILE_PATH = path;
	}
	
	public static String BIZ_FILE_PATH() {
		return BIZ_FILE_PATH;
	}


	/* 
	 * 전자문서프로세스ID 
	*/
	private static String EDOC_PROCESS_ID;
	
	@Value("#{inziProperty['id.process.edoc']}")
	private void setEDOC_PROCESS_ID(String eDOC_PROCESS_ID) {
		EDOC_PROCESS_ID = eDOC_PROCESS_ID;
	}
	
	public static String EDOC_PROCESS_ID() {
		return EDOC_PROCESS_ID;
	}
}
