package com.mobileleader.edoc.monitoring.service;

import java.util.HashMap;
import java.util.List;

import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.common.model.EdocProcAggrItem;
import com.mobileleader.edoc.monitoring.db.dto.EdocFileProcsMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsErrHstrDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsStatisticsDto;

public interface EdocGroupProcsMgmtService {
	
	/**
	 * 전자문서 처리 결과 데이터를 받아온다.
	 * 
	 * @param edocProcListForm
	 * @return
	 */
	public List<EdocProcAggrItem> getProcAggregate(EdocProcListForm edocProcListForm);
	
	/**
	 * 전자문서 전체 건 수를 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public int getProcTotalCount(EdocProcListForm edocProcListForm);
	
	/**
	 * 전자문서 목록을 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public List<EdocGroupProcsMgmtDto> getProcList(EdocProcListForm edocProcListForm);
	
	/**
	 * ECM에 PDF가 전송된 전자문서 전체 건 수를 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public int getRegistEcmProcTotalCount(EdocProcListForm edocProcListForm);
	
	/**
	 * ECM에 PDF가 전송된 전자문서 목록을 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public List<EdocGroupProcsMgmtDto> getRegistEcmProcList(EdocProcListForm edocProcListForm);
	
	/**
	 * 전자문서 상세 정보를 가져온다.
	 * @param elecDocGroupInexNo
	 * @return
	 */
	public EdocGroupProcsMgmtDto getProcDetail(String elecDocGroupInexNo);
	
	/**
	 * 전자문서 처리 오류 전체 건 수를 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public int getProcErrorHistoryTotalCount(EdocProcListForm edocProcListForm);
	
	/**
	 * 전자문서 처리 오류 목록을 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public List<EdocGroupProcsErrHstrDto> getProcErrorHistoryList(EdocProcListForm edocProcListForm);
	
	/**
	 * 전자문서 파일 처리 전체 건 수를 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public int getProcFileTotalCount(EdocProcListForm edocProcListForm);
	
	/**
	 * 전자문서 파일 처리 목록을 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public List<EdocFileProcsMgmtDto> getProcFileList(EdocProcListForm edocProcListForm);
	
	/**
	 * 전자문서 파일 처리 상세를 가져온다.
	 * @param elecDocGroupInexNo
	 * @param fileSeqNo
	 * @return
	 */
	public EdocFileProcsMgmtDto getProcFile(String elecDocGroupInexNo, int fileSeqNo);
	
	/**
	 * 전자문서 파일 처리를 재시도하도록 변경한다.
	 * @param edocIdxList
	 * @return
	 */
	public int retryProcessing(List<String> edocIdxList);
	
	/**
	 * 전자문서 처리 통계 데이터를 가져온다.
	 * @param edocProcListForm
	 * @return
	 */
	public List<EdocGroupProcsStatisticsDto> getStatisticsList(EdocProcListForm edocProcListForm); 
	
	/**
	 * 전자문서 처리 통계 엑셀 다운로드를 한다.
	 * @param edocProcListForm
	 * @return
	 */
	public HashMap<String, Object> downloadExcel(EdocProcListForm edocProcListForm); 
	
	/**
	 * 전자문서 파일 경로를 가져온다.
	 * @param fileId
	 * @return
	 */
	public String getLocalStoragePdfFilePath(String fileId);
	
	
}
