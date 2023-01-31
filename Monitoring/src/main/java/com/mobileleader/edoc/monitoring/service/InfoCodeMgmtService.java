package com.mobileleader.edoc.monitoring.service;

import java.util.List;

import com.mobileleader.edoc.monitoring.common.form.InfoCodeForm;
import com.mobileleader.edoc.monitoring.db.dto.InfoCodeDto;

public interface InfoCodeMgmtService {
	
	/**
	 * 공통 코드 전체 건수를 조회한다
	 * @param infoCodeForm
	 * @return
	 */
	public int getTotalCount(InfoCodeForm infoCodeForm) throws Exception;
	
	/**
	 * 공통 코드 목록을 조회한다
	 * @param infoCodeForm
	 * @return
	 */
	public List<InfoCodeDto> getCodeList(InfoCodeForm infoCodeForm) throws Exception;
	
	/**
	 * 코드 중복을 체크한다
	 * @param infoCodeForm
	 * @return
	 */
	public int chkDupCode(InfoCodeForm infoCodeForm) throws Exception;
	
	/**
	 * 코드를 등록한다
	 * @param infoCodeForm
	 * @return
	 */
	public int registCode(InfoCodeForm infoCodeForm) throws Exception;
	
	/**
	 * 코드를 수정한다
	 * @param infoCodeForm
	 * @return
	 */
	public int modifyCode(InfoCodeForm infoCodeForm) throws Exception;
	
	/**
	 * 코드를 삭제한다
	 * @param infoCodeForm
	 * @return
	 */
	public int deleteCode(InfoCodeForm infoCodeForm) throws Exception;
	
	/**
	 * 그룹의 코드 목록을 조회한다.
	 * @param groupCode
	 * @return
	 * @throws Exception
	 */
	public List<InfoCodeDto> getGroupCodeList(String groupCode);
	
	/**
	 * 그룹의 코드 상세를 조회한다.
	 * @param groupCode
	 * @param code
	 * @return
	 */
	public InfoCodeDto getGroupCode(String groupCode, String code);
	
}
