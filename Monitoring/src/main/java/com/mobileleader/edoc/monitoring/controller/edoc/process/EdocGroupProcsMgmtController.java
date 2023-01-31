package com.mobileleader.edoc.monitoring.controller.edoc.process;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobileleader.edoc.monitoring.common.form.EdocProcListForm;
import com.mobileleader.edoc.monitoring.db.dto.EdocGroupProcsMgmtDto;
import com.mobileleader.edoc.monitoring.service.EdocGroupProcsMgmtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/edoc/proc")
@RequiredArgsConstructor
@Slf4j
public class EdocGroupProcsMgmtController {
	
	private final EdocGroupProcsMgmtService edocGroupProcsMgmtService;
	
	/**
	 * 전자문서 처리 내역 목록
	 */
	@PostMapping(value="/list")
	@Secured({"ROLE_A02_LIST", "ROLE_A03_LIST", "ROLE_A04_LIST"})
	public HashMap<String, Object> procList(@ModelAttribute("edocProcListForm") EdocProcListForm edocProcListForm) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		logger.info("[/edoc/proc/list] form.toString() : {}", edocProcListForm.toString());
		
		int totalCount = edocGroupProcsMgmtService.getProcTotalCount(edocProcListForm);
		logger.info("[/edoc/proc/list] totalCount : {}", totalCount);
		
		if (totalCount > 0) {
			List<EdocGroupProcsMgmtDto> list = edocGroupProcsMgmtService.getProcList(edocProcListForm);
			map.put("list", list);
		} 
		
		map.put("totalCount", totalCount);
		map.put("result", "success");
	
		return map;	
	}

	/**
	 * 파일 처리 재시도
	 */
	@PostMapping(value="/retry")
	@Secured("ROLE_A04_EDIT")
	public HashMap<String, Object> reprocessing(@RequestParam("edocIdxNo") List<String> edocIdxList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (edocIdxList != null && edocIdxList.size() > 0) {
			int result = edocGroupProcsMgmtService.retryProcessing(edocIdxList);
			if (result > 0) {
				map.put("result", "success");
			} else {
				map.put("result", "fail");
			}
		}
		
		
		return map;	
	}
	
}
