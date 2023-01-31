package com.mobileleader.edoc.monitoring.db.mapper;

import java.util.List;

import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsErrHstrDto;

public interface EdocGroupProcsErrHstrMapper {
	
	/**
	 * 전자문서 처리 오류 내역 
	 */
	public int count(EdocProcListForm edocProcListForm);
	public List<EdocGroupProcsErrHstrDto> selectAll(EdocProcListForm edocProcListForm);

}
