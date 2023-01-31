package com.mobileleader.edoc.monitoring.controller.dashboard;

import java.util.HashMap;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobileleader.edoc.monitoring.common.form.dashboard.DashboardForm;
import com.mobileleader.edoc.monitoring.common.model.response.StatisticsBranchResponse;
import com.mobileleader.edoc.monitoring.common.model.response.StatisticsChannelResponse;
import com.mobileleader.edoc.monitoring.exceptions.RequestValidationException;
import com.mobileleader.edoc.monitoring.service.image.ImageServerService;
import com.mobileleader.edoc.monitoring.support.validation.annotation.DateFormat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Validated
@Slf4j
public class DashboardRestController {
	
	@Autowired
	ImageServerService imageServerService;

    /**
     * 업무별 등록 건수(월별, 일별) 조회
     * 
     * @param request
     * @param errors
     * @return
     */
	@PostMapping("/biz/count")
    public StatisticsChannelResponse getProcessingStatusCounts(@Validated DashboardForm request, Errors errors) {
        validate(errors);
        
        logger.info("/dashboard/biz/count");
        
        StatisticsChannelResponse response = new StatisticsChannelResponse();
        response.setMonthlyProcessingStatusCounts(imageServerService.getChannelStatisticsFromImageServer("month", request.getSearchYearMonth()));
        response.setDailyProcessingStatusCounts(imageServerService.getChannelStatisticsFromImageServer("day", request.getSearchDate()));

        return response;
    }
    
	/**
	 * 업무별 등록 건수(월별) 조회
	 * 
	 * @param searchYearMonth
	 * @return
	 */
	@PostMapping("/biz/count/monthly")
    public List<HashMap<String, Object>> getMonthlyProcessingStatusCounts(@NotEmpty @DateFormat(pattern = "yyyy.MM")
                                                             @RequestParam String searchYearMonth) {
    	logger.info("/dashboard//biz/count/monthly");
    	
    	List<HashMap<String, Object>> response = imageServerService.getChannelStatisticsFromImageServer("month", searchYearMonth);
    	
        return response;
    }
    
	/**
	 * 업무별 등록 건수(일별) 조회
	 * 
	 * @param searchDate
	 * @return
	 */
	@PostMapping("/biz/count/daily")
    public List<HashMap<String, Object>> getDailyProcessingStatusCounts(@NotEmpty @DateFormat(pattern = "yyyy.MM.dd")
                                                           @RequestParam String searchDate) {
    	logger.info("/dashboard/biz/count/daily");
    	
    	List<HashMap<String, Object>> response = imageServerService.getChannelStatisticsFromImageServer("day", searchDate); 
    	
        return response;
    }
    
    /**
     * 통합전자문서 등록 지부 Top10 (월별, 일별) 조회
     * 
     * @param request
     * @param errors
     * @return
     */
    @PostMapping("/branches")
    public StatisticsBranchResponse getUsageTop10Branches(@Validated DashboardForm request, Errors errors) {
        validate(errors);
        
        logger.info("/dashboard/branches");
        
        StatisticsBranchResponse response = new StatisticsBranchResponse();
        response.setMonthlyUsageTop5Branches(imageServerService.getBranchStatisticsFromImageServer("month", request.getSearchYearMonth()));
        response.setDailyUsageTop5Branches(imageServerService.getBranchStatisticsFromImageServer("day", request.getSearchDate()));
        
        return response;
    }

    /**
     * 통합전자문서 등록 지부 Top10 (월별) 조회
     * 
     * @param searchYearMonth
     * @return
     */
    @PostMapping("/branches/monthly")
    public List<HashMap<String, Object>> getMonthlyUsageTop10Branches(@NotEmpty @DateFormat(pattern = "yyyy.MM")
                                                                @RequestParam String searchYearMonth) {
    	logger.info("/dashboard/branches/monthly");
    	
    	List<HashMap<String, Object>> response = imageServerService.getBranchStatisticsFromImageServer("month", searchYearMonth);
    	
    	return response;
    }
    
    /**
     * 통합전자문서 등록 지부 Top10 (일별) 조회
     * 
     * @param searchDate
     * @return
     */
    @PostMapping("/branches/daily")
    public List<HashMap<String, Object>> getDailyUsageTop10Branches(@NotEmpty @DateFormat(pattern = "yyyy.MM.dd")
                                                              @RequestParam String searchDate) {
    	logger.info("/dashboard/branches/daily");
    	
    	List<HashMap<String, Object>> response = imageServerService.getBranchStatisticsFromImageServer("day", searchDate);
    
    	return response;
    }

    private void validate(Errors errors) {
        if(errors != null && errors.hasErrors()) {
            throw new RequestValidationException("Request Validation failed", errors);
        }
    }
}
