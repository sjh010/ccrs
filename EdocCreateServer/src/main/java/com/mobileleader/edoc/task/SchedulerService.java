package com.mobileleader.edoc.task;

import org.springframework.scheduling.Trigger;

/**
 * 스케줄러 서비스 인터페이스
 * 
 * @author yh.kim
 *
 */
public interface SchedulerService {
	
	/**
	 * 스케줄러 사용여부 리턴
	 */
	public boolean isUsed();

	/**
	 * 스케줄러 실행 Trigger 리턴
	 */
	public Trigger getTrigger();
	
	/**
	 * 스케줄러 프로세스 함수
	 */
	public void execute() throws Exception;

}
