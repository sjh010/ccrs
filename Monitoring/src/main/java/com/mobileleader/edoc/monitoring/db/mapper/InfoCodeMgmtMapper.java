package com.mobileleader.edoc.monitoring.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mobileleader.edoc.monitoring.common.form.InfoCodeForm;
import com.mobileleader.edoc.monitoring.db.dto.InfoCodeDto;

public interface InfoCodeMgmtMapper {

	/**
	 * 공통 코드 전체 건수를 조회한다
	 * @param infoCodeForm
	 * @return
	 */
	public int selectTotalCount(InfoCodeForm infoCodeForm);
	
	/**
	 * 공통 코드 목록을 조회한다
	 * @param infoCodeForm
	 * @return
	 */
	public List<InfoCodeDto> selectCodeList(InfoCodeForm infoCodeForm);

	/**
	 * 코드를 등록한다
	 * @param infoCodeDto
	 * @return
	 */
	public int insertCode(InfoCodeDto infoCodeDto);
	
	/**
	 * 코드를 수정한다
	 * @param infoCodeDto
	 * @return
	 */
	public int updateCode(InfoCodeDto infoCodeDto);
	
	/**
	 * 코드를 삭제한다
	 * @param infoCodeForm
	 * @return
	 */
	public int deleteCode(InfoCodeForm infoCodeForm);
	
	/**
	 * 그웁의 코드 목록을 조회한다.
	 * @param groupCode
	 * @return
	 */
	public List<InfoCodeDto> selectGroupCodeAll();
	
	/**
	 * 그웁의 코드 목록을 조회한다.
	 * @param groupCode
	 * @return
	 */
	public List<InfoCodeDto> selectGroupCodeList(@Param("cdGroup") String groupCode);
	
	/**
	 * 그룹의 코드 상세를 조회한다.
	 * @param groupCode
	 * @param code
	 * @return
	 */
	public InfoCodeDto selectGroupCode(@Param("cdGroup") String groupCode, @Param("cdVal") String code);
}
