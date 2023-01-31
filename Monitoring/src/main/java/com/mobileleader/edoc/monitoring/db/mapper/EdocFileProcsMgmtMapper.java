package com.mobileleader.edoc.monitoring.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.db.dto.EdocFileProcsMgmtDto;

public interface EdocFileProcsMgmtMapper {
	
	/**
	 * 전자문서 파일 처리 내역
	 */
	public int count(EdocProcListForm edocProcListForm);
	public List<EdocFileProcsMgmtDto> selectAll(EdocProcListForm edocProcListForm);
	public EdocFileProcsMgmtDto select(@Param("elecDocGroupInexNo") String elecDocGroupInexNo, @Param("fileSeqNo") int fileSeqNo);

}
