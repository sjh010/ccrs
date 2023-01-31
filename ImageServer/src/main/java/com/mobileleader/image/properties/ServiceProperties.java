package com.mobileleader.image.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 서비스 Property
 * 
 * @author yh.kim
 */
@Repository
public class ServiceProperties {
	
	/*
	 * 메인키 채번 시, 테스트서버와 운영서버를 구분하기 위한 값 
	 */
	private static final String TEST_PROFILE_KEY = "9";			// 로컬 & 테스트서버 키값
	private static final String PROD_PROFILE_KEY = "1";			// 운영서버 키값
	
	/*
	 * 운영서버 profile 설정값
	 */
	private static final String PROD_PROFILE_NAME = "prd";
	
	
	private static String profile;								// 서버 profile
	
	@Value("${spring.profiles.active:loc}")
	private void setProfile(String _profile) {
		profile = _profile;
	}
	
	public static String getProfileKey() {
		if(profile.startsWith(PROD_PROFILE_NAME)) {
			return PROD_PROFILE_KEY;
		}
		return TEST_PROFILE_KEY;
	}
}
