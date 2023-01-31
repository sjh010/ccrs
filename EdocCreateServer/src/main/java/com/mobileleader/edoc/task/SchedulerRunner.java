package com.mobileleader.edoc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

/**
 * 스케줄러 실행 메인 클래스
 * 
 * @author yh.kim
 */
@Service
public class SchedulerRunner implements InitializingBean{	
	
	private Logger logger = LoggerFactory.getLogger(SchedulerRunner.class);
	
	@Autowired
	private TaskScheduler executor;
	
	@Autowired
	@Qualifier("deleteTmpStrgMgmt")
	private SchedulerService deleteTmpInfoService;
	
	@Autowired
	@Qualifier("deleteProcsMgmt")
	private SchedulerService deleteProcsMgmtService;
	
	@Autowired
	@Qualifier("eformVerChk")
	private SchedulerService eformVerChk;
	
	/*
	 * 스케줄러 등록
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
		logger.info("---------------------------SchedulerRunner---------------------------");
		
		addTasks(deleteTmpInfoService);
		addTasks(deleteProcsMgmtService);
		addTasks(eformVerChk);
	}
	
	private void addTasks(SchedulerService service) {
		try {
			if(service.isUsed()) {
					executor.schedule(
							new Thread(() -> {
										try {
											service.execute();
										} catch (Exception e) {
											logger.error("Scheduler Error!!", e);
										}
							}), service.getTrigger());
			}
		}catch(Exception e) {
			logger.error("{} regist Failed.", service.toString(), e);
		}
		logger.info("{} is registered.", service.toString());
	}
}