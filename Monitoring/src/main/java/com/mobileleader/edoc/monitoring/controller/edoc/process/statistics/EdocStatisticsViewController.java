package com.mobileleader.edoc.monitoring.controller.edoc.process.statistics;

import java.util.HashMap;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.mobileleader.edoc.monitoring.common.annotation.ViewController;
import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.common.type.GroupCode;
import com.mobileleader.edoc.monitoring.service.EdocGroupProcsMgmtService;
import com.mobileleader.edoc.monitoring.service.InfoCodeMgmtService;
import lombok.RequiredArgsConstructor;

@ViewController
@RequestMapping(value="/edoc/proc/statistics")
@RequiredArgsConstructor
public class EdocStatisticsViewController {

    private final EdocGroupProcsMgmtService edocGroupProcsMgmtService;
    
    private final InfoCodeMgmtService infoCodeMgmtService;
    
    /**
     * 전자문서 처리 통계 화면
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    @Secured("ROLE_A03_ACCS")
    public ModelAndView statistics(@ModelAttribute("edocProcListForm") EdocProcListForm edocProcListForm) {
        ModelAndView mav = new ModelAndView();
        
        edocProcListForm.setStartDate(edocProcListForm.getStartDate().substring(0, 8)+"0000");
        edocProcListForm.setEndDate(edocProcListForm.getEndDate().substring(0, 8)+"2359");
        
        mav.addObject("procsTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_TYPE_CODE.getCode()));
        mav.addObject("procsStepStatusCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_STATUS_CODE.getCode()));
        mav.addObject("procsStepCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_CODE.getCode()));
        mav.addObject("workTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_TYPE_CODE.getCode()));
        mav.addObject("workCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_CODE.getCode()));
        mav.setViewName("/edoc/proc/procStatisticsList");
        
        return mav;
    }
    
    /**
     * 전자문서 처리 통계 상세 화면
     */
    @GetMapping(value="/detail")
    public ModelAndView statisticsDetail(@ModelAttribute("edocStatProcListForm") EdocProcListForm edocProcListForm) {
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("procsTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_TYPE_CODE.getCode()));
        mav.addObject("procsStepStatusCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_STATUS_CODE.getCode()));
        mav.addObject("procsStepCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_CODE.getCode()));
        mav.addObject("workTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_TYPE_CODE.getCode()));
        mav.addObject("workCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_CODE.getCode()));
        mav.setViewName("/edoc/proc/procStatisticsDetailPopup");
        
        return mav;
    }
    
    /**
     * 전자문서 처리 통계 > 엑셀 다운로드
     */
    @GetMapping(value="/excel")
    @Secured("ROLE_A03_LIST")
    public ModelAndView statisticsExcel(@ModelAttribute("edocStatProcListForm") EdocProcListForm edocProcListForm) {
        HashMap<String, Object> map = edocGroupProcsMgmtService.downloadExcel(edocProcListForm);
        return new ModelAndView("excelView", "map", map);
    }
}
