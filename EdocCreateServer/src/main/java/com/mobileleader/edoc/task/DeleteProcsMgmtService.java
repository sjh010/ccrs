package com.mobileleader.edoc.task;

import java.util.Calendar;

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

import com.mobileleader.edoc.db.dto.EdocGrpProcsMgmtDto;
import com.mobileleader.edoc.db.mapper.EdocGrpBzwkInfoMapper;
import com.mobileleader.edoc.db.mapper.EdocGrpProcsMgmtMapper;

/**
 * 사용하지 않은 전자문서그룹 데이터 삭제 스케줄러
 * (스케줄러 사용하지 않을 경우, properties파일에서  task.usage.deleteProcsMgmt 값 N으로 변경)
 * 
 * @author yh.kim
 */
@Service("deleteProcsMgmt")
public class DeleteProcsMgmtService implements SchedulerService {

	private Logger logger = LoggerFactory.getLogger(DeleteProcsMgmtService.class);
	
	/*
	 * 스케줄러 사용 여부
	 */
	@Value("#{inziProperty['task.usage.deleteProcsMgmt']}")
	private String isUsed;
	
	/*
	 *  스케줄러 실행 시각 - Cron 표현식
	 */
	@Value("#{inziProperty['task.cron.deleteProcsMgmt']}")
	private String cron;
	
	/*
	 * 데이터 저장 기간 - 단위 : hours
	 * (ex. '12'의 경우 12시간까지 보관. 그 이전의 데이터 및 파일 삭제
	 *  	스케줄러 실행 시각이 7월12일 19시 00분 일 경우, 7월12일 07시 00분 이전의 데이터 삭제)
	 */
	@Value("#{inziProperty['task.period.deleteProcsMgmt']}")
	private Integer PROCS_STORAGE_PERIOD;
	
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
		logger.info("deleteProceMgmtScheduler Cron Expression = {}", cron);
		logger.info("deleteProceMgmtScheduler Storage Period = {}", PROCS_STORAGE_PERIOD);
		
		return new CronTrigger(cron);
	}
	
	@Override
	@Transactional(propagation=Propagation.NESTED, isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
	public void execute() throws Exception {
		
		logger.info("---------------[Delete ProcsMgmt Scheduler - START]---------------");

		// 1. 삭제 기준 시간 계산
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -1 * PROCS_STORAGE_PERIOD);
		
		//삭제 조건 setting
		EdocGrpProcsMgmtDto delCondition = new EdocGrpProcsMgmtDto();
		delCondition.setProcsStepEdTime(calendar.getTime());
		
		// 2.BZWK_INFO 테이블에서 데이터 삭제
		int result1 = bzwkMapper.deleteProcsMgmtData(delCondition);
		
		// 3. PROCS_MGMT 테이블에서 데이터 삭제 
		int result2 = procsMgmtMapper.deleteProcsMgmtData(delCondition);

		logger.debug("deleted data cnt = [{}, {}]", result1, result2);

		logger.info("[Delete ProcsMgmt Scheduler - END]");
		
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
