package com.mobileleader.edoc.monitoring.controller.edoc.process.current;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mobileleader.edoc.monitoring.common.annotation.ViewController;
import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.common.type.GroupCode;
import com.mobileleader.edoc.monitoring.db.dto.EdocFileProcsMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsErrHstrDto;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsMgmtDto;
import com.mobileleader.edoc.monitoring.service.EdocGroupProcsMgmtService;
import com.mobileleader.edoc.monitoring.service.InfoCodeMgmtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ViewController
@RequestMapping(value="/edoc/proc/current")
@RequiredArgsConstructor
@Slf4j
public class EdocCurrentViewController {

    private final EdocGroupProcsMgmtService edocGroupProcsMgmtService;
    
    private final InfoCodeMgmtService infoCodeMgmtService;
    
    /**
     * 전자문서 처리 현황 화면
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    @Secured("ROLE_A02_ACCS")
    public ModelAndView current(@ModelAttribute("edocProcListForm") EdocProcListForm edocProcListForm) {
    	
    	logger.info("/edoc/proc/current/");
    	
        ModelAndView mav = new ModelAndView();
        
        Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String today = formatter.format(date);
		
		String startDate = edocProcListForm.getStartDate();
		String endDate = edocProcListForm.getEndDate();

		edocProcListForm.setStartDate((StringUtils.isEmpty(startDate)) ? today : startDate);
		edocProcListForm.setEndDate((StringUtils.isEmpty(endDate)) ? today : endDate);
        
        mav.addObject("procsStepStatusCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_STATUS_CODE.getCode()));
        mav.addObject("procsStepCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_CODE.getCode()));
        mav.addObject("procsTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_TYPE_CODE.getCode()));
        mav.addObject("workCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_TYPE_CODE.getCode()));
        mav.setViewName("/edoc/proc/procCurrentStateList");
        
        return mav;
    }
    
    /**
     * 전자문서 처리 현황 상세 화면
     */
    @GetMapping(value="/{elecDocGroupInexNo}")
    @Secured("ROLE_A02_DTAL")
    public ModelAndView currentDetail(@PathVariable("elecDocGroupInexNo") String elecDocGroupInexNo) {
        ModelAndView mav = new ModelAndView();
        
        logger.info("/edoc/proc/current/" + elecDocGroupInexNo);
        
        EdocGroupProcsMgmtDto detail = edocGroupProcsMgmtService.getProcDetail(elecDocGroupInexNo);
        
        EdocProcListForm edocProcListForm = new EdocProcListForm();
        edocProcListForm.setElecDocGroupInexNo(elecDocGroupInexNo);
        
        // 파일 처리 내역
        int countProcFile = edocGroupProcsMgmtService.getProcFileTotalCount(edocProcListForm);
        if (countProcFile > 0) {
            List<EdocFileProcsMgmtDto> procFileList = edocGroupProcsMgmtService.getProcFileList(edocProcListForm);
            detail.setProcFiles(procFileList);
        }
        
        // 에러 내역
        int countErrorHistory = edocGroupProcsMgmtService.getProcErrorHistoryTotalCount(edocProcListForm);
        if (countErrorHistory > 0) {
            List<EdocGroupProcsErrHstrDto> errorHistoryList = edocGroupProcsMgmtService.getProcErrorHistoryList(edocProcListForm);
            detail.setErrorHistory(errorHistoryList);
        }
        
        mav.addObject("procsStepStatusCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_STATUS_CODE.getCode()));
        mav.addObject("procsStepCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_CODE.getCode()));
        mav.addObject("procsStepMessageCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_MESSAGE_CODE.getCode()));
        mav.addObject("detail", detail);
        mav.setViewName("/edoc/proc/procCurrentStateDetailPopup");
        
        return mav;
    }
}
