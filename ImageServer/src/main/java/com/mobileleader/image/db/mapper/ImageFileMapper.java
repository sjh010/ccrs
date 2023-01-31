package com.mobileleader.image.db.mapper;


import java.util.ArrayList;

import com.mobileleader.image.db.dto.ImageFileDto;

/**
 * IMAGE_FILE Table DAO
 *  
 * @author bkcho
 * @since 2019.06.24
 *
 */
public interface ImageFileMapper {
	
	/*
	 * selectList
	 * 1. 최신 버전 파일 리스트 조회			->	default
	 * 2. 모든 버전 파일 리스트 조회			->	검색조건에  versionInfo = 999 추가
	 * 3. 삭제처리 파일 제외한 파일 리스트 조회	->	검색조건에 deleteYn = 'N' 추가
	 * 4. 삭제처리 파일 포함한 파일 리스트 조회	->	default
	 */
	public ArrayList<ImageFileDto> selectList(ImageFileDto condition);	
	
	/*
	 * 동일한 doc-id존재할 경우 version 업그레이드하여 insert
	 * 존재하지 않을 경우 default값(1)로 insert
	 */
	public int insertWithVersionInfo(ImageFileDto dto);
	
	/*
	 * deleteYn 값 update 
	 */
	public int updateDeleteYn(ImageFileDto dto);
	
}
