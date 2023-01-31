package com.mobileleader.image.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.db.mapper.ImageFileMapper;
import com.mobileleader.image.db.mapper.ImageStoreMapper;
import com.mobileleader.image.server.model.request.SearchRequest;
import com.mobileleader.image.server.model.response.SearchResponse;

/**
 * ECM 조회 서비스 구현 클래스
 * 
 * @author bkcho@mobileleader.com
 * @since 2019.06.24
 *
 */
@Service
public class SearchServiceImpl implements SearchService{
	
	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Autowired
	private SqlSession sqlSession;
	
	/*
	 * ECM 서버에 업로드된 파일 조회
	 * 1. 특정 docId 파일 검색 			-> 	검색 조건에 docId 명시
	 * 2. 최신 버전 파일 리스트 조회			->	default
	 * 3. 모든 버전 파일 리스트 조회			->	검색조건에  versionInfo = 999 추가
	 * 4. 삭제처리 파일 제외한 파일 리스트 조회	->	검색조건에 deleteYn = 'N' 추가
	 * 5. 삭제처리 파일 포함한 파일 리스트 조회	->	default
	 */
	@Override
	public SearchResponse search(SearchRequest searchReq)  throws Exception{
		SearchResponse res = new SearchResponse();
		
		// 검색 조건
		ImageFileDto condition = searchReq.getImageFileDto();
		
		String mainKey = condition.getMainKey();
		
		// ImageStore 검색
		ImageStoreMapper imageStoreMapper = sqlSession.getMapper(ImageStoreMapper.class);
		ImageStoreDto imageStoreDto = imageStoreMapper.selectByPrimaryKey(mainKey);
	
		if (imageStoreDto == null) {
			logger.info("Requested Data Does Not Exist");
			
			res.setResultCode(90000);
			res.setResultMessage("Requested Data Does Not Exist");
			res.setImageStoreDto(new ImageStoreDto());
			res.setImageFileDto(new ArrayList<ImageFileDto>());
			
			return res;
		}
		
		// ImageFile 검색 - 검색 결과 없어도 Exception처리 x.
		ImageFileMapper infoDao = sqlSession.getMapper(ImageFileMapper.class);
		ArrayList<ImageFileDto> dtos = infoDao.selectList(condition);
		
		// set searchResponse
		res.setImageStoreDto(imageStoreDto);
		res.setImageFileDto(dtos);
		res.setResultCode(200);
		res.setResultMessage("OK");
		
		return res;
	}
	
//	public MainkeyListResponse searchFileId(String mainKey) {
//		logger.debug("searchFileId mainkey={0}, docId={1}", mainKey, "");
//		
//		MainkeyListResponse searchRes = new MainkeyListResponse();
// 
//		try {
//			ImageFileDto dto = new ImageFileDto();
//			dto.setMainKey(mainKey);
//						
//			ImageFileMapper infoDao = sqlSession.getMapper(ImageFileMapper.class);
//			ArrayList<ImageFileDto> dtos = infoDao.select(dto);
//			
//			if (dtos != null )
//			{
//				if (dtos.size() > 0) {
//					searchRes.setMainKey(mainKey);
//					for (ImageFileDto imageInfoDto : dtos) {
//						ImageFileRequest imageInfo = new ImageFileRequest();
//						imageInfo.setDocId(imageInfoDto.getDocId());
//						imageInfo.setFileId(imageInfoDto.getFileId());
//						String strDate = CommonUtil.getDateToString(imageInfoDto.getCreateTime().getTime(), null);
//						imageInfo.setCreateTime(strDate);
//						searchRes.setImageInfo(imageInfo);
//					}
//					
//				}				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return searchRes;
//	}

}
