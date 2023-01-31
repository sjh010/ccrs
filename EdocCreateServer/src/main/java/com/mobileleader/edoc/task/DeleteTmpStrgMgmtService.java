package com.mobileleader.edoc.task;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;
import com.mobileleader.edoc.db.mapper.EdocGrpBzwkInfoMapper;
import com.mobileleader.edoc.db.mapper.EdocGrpProcsMgmtMapper;
import com.mobileleader.edoc.db.mapper.EdocGrpTmpStrgMgmtMapper;

/**
 * 저장기한이 만효된 임시저장 데이터 및 파일 삭제 스케줄러
 * (스케줄러 사용하지 않을 경우 properties파일에서 task.usage.deleteTmpStrgMgmt 값 N으로 변경)
 * 
 * @author yh.kim
 */

@Service("deleteTmpStrgMgmt")
public class DeleteTmpStrgMgmtService implements SchedulerService{
	
	private Logger logger = LoggerFactory.getLogger(DeleteTmpStrgMgmtService.class);
	
	/*
	 * 스케줄러 사용 여부
	 */
	@Value("#{inziProperty['task.usage.deleteTmpStrgMgmt']}")
	private String isUsed;
	
	/*
	 *  스케줄러 실행  시각 - Cron 표현식
	 */
	@Value("#{inziProperty['task.cron.deleteTmpStrgMgmt']}")
	private String cron;
	
	/*
	 * 임시저장 관리정보 보관 기간 - 단위 : 일
	 *  (ex. '10'의 경우 10일까지 보관. 그 이후의 데이터 및 파일 삭제
	 *  	스케줄러 실행 시각이 7월12일 13시 00분 일 경우, 7월2일 13시 00분 이전의 데이터 모두 삭제)
	 */
	@Value("#{inziProperty['task.period.deleteTmpStrgMgmt']}")
	private Integer TMP_STORAGE_PERIOD;
	
	@Autowired
	private EdocGrpTmpStrgMgmtMapper tmpStrgMgmtMapper;
	
	@Autowired
	private EdocGrpProcsMgmtMapper procsMgmtMapper;
	
	@Autowired
	private EdocGrpBzwkInfoMapper bzwkMapper;
	
	@Override
	public boolean isUsed() {
		if(isUsed != null && isUsed.equalsIgnoreCase("Y")) {
			return true;
		}
		return false;
	}
	
	@Override
	public Trigger getTrigger() {
		logger.info("deleteTmpStrgMgmtScheduler Cron Expression = {}", cron);
		logger.info("deleteTmpStrgMgmtScheduler Storage Period = {}", TMP_STORAGE_PERIOD);
		
		return new CronTrigger(cron);
	}
	
	@Override
	@Transactional(propagation=Propagation.NESTED, isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
	public void execute() throws Exception {
		logger.info("---------------[Delete TempStrgMgmt Scheduler - START]---------------");
		
		try {
			//EDS_ELEC_DOC_GROUP_TMP_STRG_MGMT 테이블 데이터 삭제  프로세스
			List<EdocGrpTmpStrgMgmtDto> tmpStrgDelList = deleteFromTmpStrgTable();
			if(tmpStrgDelList == null) {
				logger.info("No Data to Delete.");
				return;
			}
			
			//EDS_ELEC_DOC_GROUP_PROCS_MGMT 테이블 데이터 삭제 프로세스
			deleteFromProcsMgmtTable(tmpStrgDelList);
		
		}finally {
			logger.info("[Delete TempMgmtInfo Scheduler - END]");
		}
	}
	
	/*
	 * 임시저장 관리정보테이블에서 데이터 및 임시저장 파일 삭제
	 */
	private List<EdocGrpTmpStrgMgmtDto> deleteFromTmpStrgTable() throws SQLException{
		
		// 1. 삭제 기준 시간 계산
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1 * TMP_STORAGE_PERIOD);
			
		// 2. 삭제조건에 해당하는 임시저장 관리정보 리스트 조회
		List<EdocGrpTmpStrgMgmtDto> tmpStrgDelList = tmpStrgMgmtMapper.selectDelList(calendar.getTime());
		
		if(tmpStrgDelList == null || tmpStrgDelList.size() == 0) {
			return null;
		}
		
		// 3. 임시저장 파일 삭제
		tmpStrgDelList.parallelStream().forEach(tmpStrgMgmtVo -> deleteTmpStrgFile(tmpStrgMgmtVo));
		
		// 4. EDS_ELEC_DOC_GROUP_TMP_STRG_MGMT 테이블에서 데이터 삭제
		int result = tmpStrgMgmtMapper.deleteAll(tmpStrgDelList);

		if(result != -1) {
			throw new SQLException("Deleted Data Count in TmpStrgMgmt Table is Invalid");
		}
			
		return tmpStrgDelList;		
	}
	
	/*
	 * 전자문서 그룹 프로세스 관리정보테이블에서 데이터 삭제
	 * : 임시저장 후 전자문서생성 요청을 하지 않은 경우, 해당 데이터의 전자문서 그룹 프로세스 관리정보도 함께 삭제한다.
	 */
	private void deleteFromProcsMgmtTable(List<EdocGrpTmpStrgMgmtDto> tmpStrgDelList) throws SQLException {
		
		// 1. 삭제조건에 해당하는 전자문서 그룹 프로세스 관리정보 리스트 삭제
		int res1 = bzwkMapper.deleteTmpMgmtData(tmpStrgDelList);
		
		int res2 = procsMgmtMapper.deleteTmpMgmtData(tmpStrgDelList);
		
		logger.debug("deleted data cnt = {}, {}", res1, res2);
	}
	

	/**
	 * 임시저장 데이터파일 삭제
	 */
	private void deleteTmpStrgFile(EdocGrpTmpStrgMgmtDto vo) {
		String svrFilePath = vo.getSvrFile();
		File svrFile = new File(svrFilePath);
		
		if(svrFile.exists()) {
			svrFile.delete();
			logger.debug("File Deleted. EdocIdxNo = {}, MappingKey = {}, FilePath = {}", vo.getElecDocGroupInexNo(), vo.getMappingKey(), svrFilePath);
		}else {
			logger.debug("File does not Exist. EdocIdxNo = {}, MappingKey = {}, FilePath = {}", vo.getElecDocGroupInexNo(), vo.getMappingKey(), svrFilePath);
		}
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
