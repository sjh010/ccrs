package com.mobileleader.edoc.task;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.mobileleader.edoc.properties.EdocServiceProp;
import com.mobileleader.edoc.util.XmlUtil;

/**
 * 전자서식 버전 체크 스케줄러
 * ${EFORM_ROOT_PATH}/form/EDOC_VerCheck.xml, ${EFORM_ROOT_PATH}/biz/BIZ_VerCheck.xml 생성
 * (스케줄러 사용하지 않을 경우, properties파일의 task.usage.eformVerChk 값 N으로 변경)
 *  
 * @author user
 */
@Service("eformVerChk")
public class EformVerChkService implements SchedulerService {
	
	private Logger logger = LoggerFactory.getLogger(EformVerChkService.class);
	
	/*
	 * 스케줄러 사용여부
	 */
	@Value("#{inziProperty['task.usage.eformVerChk']}")
	private String isUsed;
	
	/*
	 * 스케줄러 실행 시각 - Cron 표현식
	 */
	@Value("#{inziProperty['task.cron.eformVerChk']}")
	private String cron;
	

	@Override
	public boolean isUsed() {
		if(isUsed != null && isUsed.equalsIgnoreCase("Y")) {
			return true;
		}
		return false;
	}

	@Override
	public Trigger getTrigger() {		
		logger.info("eformVersionCheckScheduler Cron Expression = {}", cron);
		
		return new CronTrigger(cron);
	}

	@Override
	public void execute() throws Exception {
		logger.info("---------------[Eform Version Check Scheduler - START]---------------");
		
		String formPath = EdocServiceProp.EFORM_ROOT_PATH() + EdocServiceProp.FORM_FILE_PATH();
		String bizPath = EdocServiceProp.EFORM_ROOT_PATH() + EdocServiceProp.BIZ_FILE_PATH();
		
		if(new File(formPath).exists() && new File(bizPath).exists()) {
			XmlUtil util = new XmlUtil(formPath, bizPath);
			util.generateVersionXml();
		}else {
			logger.error("target folder not extist");
		}
		
		logger.info("---------------[Eform Version Check Scheduler - END]---------------");
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
