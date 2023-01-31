package com.mobileleader.edoc.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mobileleader.edoc.db.dto.EdocGrpProcsMgmtDto;
import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;

/**
 * 전자문서서버에서 전자문서 처리 시 필요한 전자문서그룹의 정보를 관리하는 메인 테이블 DAO (EDS_ELEC_DOC_GROUP_PROCS_MGMT table)
 *
 */
public interface EdocGrpProcsMgmtMapper {
	
	/*
	 * 전자문서그룹처리관리 테이블에 전자문서그룹 처리관리 데이터 추가
	 */
	public int insert(EdocGrpProcsMgmtDto tbElecDocGroupProcsMgmtVo);
	
	/*
	 * 전자문서키(전자문서그룹인덱스번호)로 전자문서그룹 처리관리 데이터 검색
	 */
	public EdocGrpProcsMgmtDto selectByPk(String elecDocGroupIndexNo);
	
	/*
	 * 전자문서그룹처리관리 테이블에서 update
	 */
	public int updateGeneral(@Param("newValue") EdocGrpProcsMgmtDto newValue, @Param("condition") EdocGrpProcsMgmtDto condition);
	
	/*
	 * 전자문서그룹처리관리 테이블 update
	 */
	public int update(EdocGrpProcsMgmtDto tbElecDocGroupProcsMgmtVo);
	
	/*
	 * 사용되지 않은 전자문서그룹의 처리관리 데이터 삭제
	 */
	public int deleteProcsMgmtData(EdocGrpProcsMgmtDto delCondition);
	
	/*
	 * 임시저장 기간 만료된 전자문서그룹리스트의 처리관리 데이터 삭제
	 */
	public int deleteTmpMgmtData(List<EdocGrpTmpStrgMgmtDto> list);

}
