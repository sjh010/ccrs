package com.mobileleader.edoc.monitoring.controller.edoc.process.monitoring;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.mobileleader.edoc.monitoring.common.annotation.ViewController;
import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import lombok.RequiredArgsConstructor;

@ViewController
@RequestMapping(value="/edoc/proc/monitoring")
@RequiredArgsConstructor
public class EdocMonitoringViewController {

    /**
     * 전자문서 처리결과 모니터링 화면
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    @Secured("ROLE_A01_ACCS")
    public ModelAndView monitoring(@ModelAttribute("edocProcListForm") EdocProcListForm edocProcListForm) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/edoc/proc/procMonitoringList");
        return mav;
    }
}
