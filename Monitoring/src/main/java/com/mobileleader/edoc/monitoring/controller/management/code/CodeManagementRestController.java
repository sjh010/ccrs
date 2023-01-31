package com.mobileleader.edoc.monitoring.controller.management.code;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobileleader.edoc.monitoring.common.form.InfoCodeForm;
import com.mobileleader.edoc.monitoring.common.type.GroupCode;
import com.mobileleader.edoc.monitoring.db.dto.InfoCodeDto;
import com.mobileleader.edoc.monitoring.service.InfoCodeMgmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/info/codeAdmin")
@RequiredArgsConstructor
@Slf4j
public class CodeManagementRestController {

	private final InfoCodeMgmtService infoCodeMgmtService;
	
	/**
	 * 업무 코드 목록을 조회한다.
	 * @param infoCodeForm
	 * @return
	 */
	@PostMapping(value="/work/{workType}")
	@Secured("ROLE_C01_LIST")
	public HashMap<String, Object> getWorkCodeList(@PathVariable String workType) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (workType != null && workType.equals("ALL")) {
			map.put("workCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_CODE.getCode()));
		} else {
			map.put("workCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_CODE.getCode()));
		}
		map.put("result", "success");
			
		return map;	
	}
	
	/**
	 * 코드목록을 조회한다
	 * @param infoCodeForm
	 * @return
	 */
	@PostMapping(value="/getCodeList")
	@Secured("ROLE_C01_LIST")
	public HashMap<String, Object> getCodeList(@ModelAttribute("infoCodeForm") InfoCodeForm infoCodeForm) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			logger.debug("[/info/codeAdminlist/getCodeList] form.toString() : {}", infoCodeForm.toString());

			int totalCount = infoCodeMgmtService.getTotalCount(infoCodeForm);
			List<InfoCodeDto> codeList = new ArrayList<InfoCodeDto>();
			if(totalCount > 0) codeList = infoCodeMgmtService.getCodeList(infoCodeForm);
			
			map.put("totalCount", totalCount);
			map.put("codeList", codeList);
			map.put("result", "success");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 목록 조회 실패");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 목록 조회 실패");
		}
		return map;	
	}
	
	/**
	 * 코드 중복을 체크한다
	 * @param infoCodeForm
	 * @return
	 */
	@PostMapping(value="/chkDupCode")
	public HashMap<String, Object> chkDupCode(@ModelAttribute("infoCodeForm") InfoCodeForm infoCodeForm) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			logger.debug("[/info/codeAdminlist/chkDupCode] form.toString() : {}", infoCodeForm.toString());

			int result = infoCodeMgmtService.chkDupCode(infoCodeForm);
			
			map.put("result", "success");
			map.put("existCode", result);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 중복 체크 실패");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 중복 체크 실패");
		}
		return map;	
	}
	
	/**
	 * 코드를 등록한다
	 * @param infoCodeForm
	 * @return
	 */
	@PostMapping(value="/registCode")
	@Secured("ROLE_C01_REG")
	public HashMap<String, Object> registCode(@ModelAttribute("infoCodeForm") InfoCodeForm infoCodeForm) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			logger.debug("[/info/codeAdminlist/registCode] form.toString() : {}", infoCodeForm.toString());

			infoCodeMgmtService.registCode(infoCodeForm);
			map.put("result", "success");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 등록 실패");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 등록 실패");
		}
		return map;	
	}
	
	/**
	 * 코드를 수정한다
	 * @param infoCodeForm
	 * @return
	 */
	@PostMapping(value="/modifyCode")
	@Secured("ROLE_C01_EDIT")
	public HashMap<String, Object> modifyCode(@ModelAttribute("infoCodeForm") InfoCodeForm infoCodeForm) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			logger.debug("[/info/codeAdminlist/modifyCode] form.toString() : {}", infoCodeForm.toString());

			infoCodeMgmtService.modifyCode(infoCodeForm);
			map.put("result", "success");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 수정 실패");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 수정 실패");
		}
		return map;	
	}
	
	/**
	 * 코드를 삭제한다
	 * @param infoCodeForm
	 * @return
	 */
	@PostMapping(value="/deleteCode")
	@Secured("ROLE_C01_DEL")
	public HashMap<String, Object> deleteCode(@ModelAttribute("infoCodeForm") InfoCodeForm infoCodeForm) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		try {
			logger.debug("[/info/codeAdminlist/deleteCode] form.toString() : {}", infoCodeForm.toString());

			infoCodeMgmtService.deleteCode(infoCodeForm);
			map.put("result", "success");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 수정 실패");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("result", "fail");
			map.put("message", "코드 수정 실패");
		}
		return map;	
	}
}

 