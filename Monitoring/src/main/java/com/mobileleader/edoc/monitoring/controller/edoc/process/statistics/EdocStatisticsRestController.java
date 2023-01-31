package com.mobileleader.edoc.monitoring.controller.edoc.process.statistics;

import java.util.HashMap;
import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsStatisticsDto;
import com.mobileleader.edoc.monitoring.service.EdocGroupProcsMgmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/edoc/proc/statistics")
@RequiredArgsConstructor
@Slf4j
public class EdocStatisticsRestController {

    private final EdocGroupProcsMgmtService edocGroupProcsMgmtService;
    
    /**
     * 전자문서 처리 통계 목록
     */
    @PostMapping(value="/list")
    @Secured("ROLE_A03_LIST")
    public HashMap<String, Object> statisticsList(@ModelAttribute("edocProcListForm") EdocProcListForm edocProcListForm) {
        HashMap<String, Object> map = new HashMap<String, Object>();
    
	    edocProcListForm.setStartDate(edocProcListForm.getStartDate().substring(0, 8)+"0000");
	    edocProcListForm.setEndDate(edocProcListForm.getEndDate().substring(0, 8)+"2359");
	    
	    logger.info("[/edoc/proc/statistics/list] form.toString() : {}", edocProcListForm.toString());
	    
	    List<EdocGroupProcsStatisticsDto> list = edocGroupProcsMgmtService.getStatisticsList(edocProcListForm);
	    
	    map.put("list", list);
	    map.put("result", "success");    
        
        return map; 
    }
    
    /**
     * 전자문서 처리 통계 상세 > 처리 내역
     */
    @PostMapping(value="/proc/list")
    @Secured("ROLE_A03_DTAL")
    public HashMap<String, Object> statisticsProcList(@ModelAttribute("edocStatProcListForm") EdocProcListForm edocProcListForm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        edocProcListForm.setStartDate(edocProcListForm.getStartDate().substring(0, 8)+"0000");
        edocProcListForm.setEndDate(edocProcListForm.getEndDate().substring(0, 8)+"2359");
        
        logger.info("[/edoc/proc/statistics/list] form.toString() : {}", edocProcListForm.toString());
        
        int totalCount = edocGroupProcsMgmtService.getProcTotalCount(edocProcListForm);
        logger.info("[/edoc/proc/statistics/list] totalCount : {}", totalCount);
        
        if (totalCount > 0) {
            List<EdocGroupProcsMgmtDto> list = edocGroupProcsMgmtService.getProcList(edocProcListForm);
            map.put("list", list);
        }
        
        map.put("totalCount", totalCount);
        map.put("result", "success");
       
        return map; 
    }
    
}
