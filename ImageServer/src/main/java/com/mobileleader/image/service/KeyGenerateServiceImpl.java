package com.mobileleader.image.service;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobileleader.image.db.mapper.CommonMapper;
import com.mobileleader.image.properties.ServiceProperties;
import com.mobileleader.image.server.model.response.KeyGenerateResponse;
import com.mobileleader.image.util.CommonUtil;

/**
 * 전자문서 통합키(MainKey) 채번 서비스 구현 클래스
 * 
 * @author user
 */
@Service
public class KeyGenerateServiceImpl implements KeyGenerateService{
	
	@Autowired
	private SqlSession sqlSession;

	private Logger logger = LoggerFactory.getLogger(KeyGenerateServiceImpl.class);
	
	/*
	 * 날짜와 DB시퀀스를 조합하여 mainKey를 생성한다.
	 * mainKey : 접수일(8:YYMMDDHH) + 서버구분키(1:운영-1, 개발-9) + 일련번호(5:1~99999)
	 * 
	 * @return KeyGenerateResponse
	 */
	@Override
	public KeyGenerateResponse generateKey() throws Exception {
		logger.debug("in generateKey");
		
		KeyGenerateResponse response = new KeyGenerateResponse();
		
		String mainKey = getMainKey();
	
		logger.debug("mainKey = {}", mainKey);
		
		response.setMainKey(mainKey);
		response.setResultCode(200);
		response.setResultMessage("OK");
		
		return response;
	}
	
	/*
	 * 키 조합하여 리턴
	 */
	public String getMainKey() {
		StringBuilder sb = new StringBuilder();
		
		int seqNo = sqlSession.getMapper(CommonMapper.class).getNextSeq();

		sb.append(CommonUtil.getDateToString(new Date(), "yyMMddHH"));
		sb.append(ServiceProperties.getProfileKey());
		sb.append(String.format("%05d", seqNo));
		
		return sb.toString();
	}
}
