package com.mobileleader.edoc.monitoring.db.mapper;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsMgmtAggrDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsStatisticsDto;
import com.mobileleader.edoc.monitoring.db.dto.dashboard.DashboardBaseDto;

public interface EdocGroupProcsMgmtMapper {

	/**
	 * 전자문서 처리 내역 
	 */
	public int count(EdocProcListForm edocProcListForm);
	public List<EdocGroupProcsMgmtDto> selectAll(EdocProcListForm edocProcListForm);
	
	public int countRegistEcm(EdocProcListForm edocProcListForm);
	public List<EdocGroupProcsMgmtDto> selectAllRegistEcm(EdocProcListForm edocProcListForm);
	
	public EdocGroupProcsMgmtDto select(@Param("elecDocGroupInexNo") String elecDocGroupInexNo);
	
	/**
	 * 전자문서 처리 결과 모니터링
	 */
	public List<EdocGroupProcsMgmtAggrDto> aggregate(EdocProcListForm edocProcListForm);
	
	/**
	 * 전자문서 처리 재처리 상태 변경
	 */
	public int updateProcsStepToReprocessingMulti(HashMap<String, Object> param);
	
	/**
	 * 전자문서 처리 재처리 상태 변경
	 */
	public int updateProcsStepToReprocessing(@Param("elecDocGroupInexNo") String elecDocGroupInexNo);
	
	/**
	 * 전자문서 취소처리 재처리 상태 변경 
	 */
	public int updateBprTrnmYnToReprocessing(@Param("guid") String guid);
	
	/**
	 * 전자문서 처리 통계
	 */
	public List<EdocGroupProcsStatisticsDto> selectStatistics(EdocProcListForm edocProcListForm);
	
	// added query for dashboard by asyu (2019-05-29)
	public DashboardBaseDto selectDashboardProcessingStatusCountForMonthly(String searchYearMonth);
	
	public DashboardBaseDto selectDashboardProcessingStatusCountForDaily(String searchDate);
}
