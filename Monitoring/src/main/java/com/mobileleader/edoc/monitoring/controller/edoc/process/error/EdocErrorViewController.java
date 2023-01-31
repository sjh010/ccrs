package com.mobileleader.edoc.monitoring.controller.edoc.process.error;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
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

@ViewController
@RequestMapping(value="/edoc/proc/error")
@RequiredArgsConstructor
public class EdocErrorViewController {

    private final EdocGroupProcsMgmtService edocGroupProcsMgmtService;
    
    private final InfoCodeMgmtService infoCodeMgmtService;
    
    /**
     * 전자문서 미처리 현황 화면
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    @Secured("ROLE_A04_ACCS")
    public ModelAndView error(@ModelAttribute("edocProcListForm") EdocProcListForm edocProcListForm) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("procsStepCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_CODE.getCode()));
        mav.addObject("procsTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_TYPE_CODE.getCode()));
        mav.setViewName("/edoc/proc/procErrorList");
        return mav;
    }
    
    /**
     * 전자 문서 미처리 현황 상세 화면
     */
    @GetMapping(value="/{elecDocGroupInexNo}")
    @Secured("ROLE_A04_DTAL")
    public ModelAndView errorDetail(@PathVariable("elecDocGroupInexNo") String elecDocGroupInexNo) {
        ModelAndView mav = new ModelAndView();
        
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
        
        mav.addObject("procsStepCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_CODE.getCode()));
        mav.addObject("procsStepMessageCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_STEP_MESSAGE_CODE.getCode()));
        mav.addObject("detail", detail);
        mav.setViewName("/edoc/proc/procErrorDetailPopup");
        
        return mav;
    }
}
