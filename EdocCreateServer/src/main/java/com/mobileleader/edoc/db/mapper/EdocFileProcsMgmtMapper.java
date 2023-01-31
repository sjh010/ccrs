package com.mobileleader.edoc.db.mapper;

import com.mobileleader.edoc.db.dto.EdocFileProcsMgmtDto;

/**
 * 전자문서 처리 시 생성되는 파일 관리 테이블 DAO (EDS_ELEC_DOC_FILE_PROCS_MGMT Table)
 * 
 */
public interface EdocFileProcsMgmtMapper {

	/*
	 * 데이터 삭제
	 */
	public int deleteGeneral(EdocFileProcsMgmtDto deleteCondition);

	/*
	 * insert
	 */
	public int insert(EdocFileProcsMgmtDto edfProcMngVo);
}
