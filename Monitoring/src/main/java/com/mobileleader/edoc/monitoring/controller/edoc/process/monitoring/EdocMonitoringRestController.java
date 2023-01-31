package com.mobileleader.edoc.monitoring.controller.edoc.process.monitoring;

import java.util.HashMap;
import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.common.model.EdocProcAggrItem;
import com.mobileleader.edoc.monitoring.service.EdocGroupProcsMgmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/edoc/proc/monitoring")
@RequiredArgsConstructor
@Slf4j
public class EdocMonitoringRestController {

    private final EdocGroupProcsMgmtService edocGroupProcsMgmtService;
    
    /**
     * 전자문서 처리 결과 내역 목록
     */
    @PostMapping(value="/list")
    @Secured("ROLE_A01_LIST")
    public HashMap<String, Object> moitoringList(@ModelAttribute("edocProcListForm") EdocProcListForm edocProcListForm) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        logger.info("[/edoc/proc/monitoring/list] form.toString() : {}", edocProcListForm.toString());
        List<EdocProcAggrItem> edocProcAggrItemList = edocGroupProcsMgmtService.getProcAggregate(edocProcListForm);
        map.put("list", edocProcAggrItemList);
        map.put("result", "success");
         
        return map; 
    }
}
