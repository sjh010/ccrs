package com.mobileleader.edoc.db.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;

/**
 * 전자문서그룹에 대한 임시 저장 관리 테이블 DAO (EDS_ELEC_DOC_GROUP_TMP_STRG_MGMT table)
 * 
 */
public interface EdocGrpTmpStrgMgmtMapper {
	
	/* 
	 * 해당 PK(전자문서 그룹키, 매핑키) 존재할 경우 update, 존재하지 않을 경우 insert 
	 */
	public int insertOrUpdate(EdocGrpTmpStrgMgmtDto bzwkInfoVo);
	
	/* 
	 * 임시저장 관리정보 조회 
	 */
	public EdocGrpTmpStrgMgmtDto selectByPk(@Param("edocGrpIdxNo") String edocGrpIdxNo, @Param("mappingKey") String mappingKey);
	
	/* 
	 * 임시저장 관리정보 리스트 조회
	 */
	public List<EdocGrpTmpStrgMgmtDto> selectList(EdocGrpTmpStrgMgmtDto condition);
	
	/* 
	 * 임시저장 관리정보 삭제 리스트 조회 
	 */
	public List<EdocGrpTmpStrgMgmtDto> selectDelList(Date delTime);
	
	/* 
	 * 임시저장 관리정보 테이블에서 데이터리스트 모두 삭제 
	 */
	public int deleteAll(List<EdocGrpTmpStrgMgmtDto> list);
}
