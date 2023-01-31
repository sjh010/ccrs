package com.mobileleader.edoc.monitoring.controller.management.user;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobileleader.edoc.monitoring.common.form.UserListForm;
import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;
import com.mobileleader.edoc.monitoring.service.UserMgmtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/info/userAdmin")
@RequiredArgsConstructor
@PropertySource(value = { "classpath:properties/ccrs.properties" })
@Slf4j
public class UserManagementRestController {
	
	private final UserMgmtService userMgmtService;

	private final Environment env;

	/**
	 * 사용자 관리 리스트 출력
	 * 
	 * @param userListForm
	 * @return
	 */
	@PostMapping(value = "/list")
	@Secured("ROLE_C02_LIST")
	public HashMap<String, Object> userList(@ModelAttribute("userListForm") UserListForm userListForm) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		logger.debug("[/info/userAdmin/list] form.toString() : {}", userListForm.toString());

		int totalCount = userMgmtService.getUserTotalCount(userListForm);
		logger.debug("[/info/userAdmin/list] totalCount : {}", totalCount);

		if (totalCount > 0) {
			List<UserMgmtDto> list = userMgmtService.getUserList(userListForm);
			map.put("list", list);
		}

		map.put("totalCount", totalCount);
		map.put("result", "success");

		return map;
	}

	/**
	 * 사용자 등록 팝업화면 저장 클릭
	 * 
	 * @param formData
	 * @return
	 */
	@PostMapping(value = "/add")
	@Secured("ROLE_C02_REG")
	public HashMap<String, Object> userAdd(@RequestParam Map<String, String> formData) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		logger.debug("[/info/userAdmin/add] formData.toString() : {}", formData.toString());

		String empNo = null;
		String empNm = null;
		int permId = 0;
		String brCd = null;
		String brnNm = null;
		String pstNm = null;
		String uzYn = null;

		if (formData.containsKey("empNo")) {
			empNo = formData.get("empNo");

			UserMgmtDto dto = userMgmtService.selectByEmpNo(empNo);

			if (dto != null) {
				map.put("result", "duplicate");
				map.put("message", "중복된 사용자 입니다.");

				return map;
			}

		} else {
			map.put("result", "fail");
			map.put("message", "empNo not exist.");
			return map;
		}

		if (formData.containsKey("empNm")) {
			empNm = formData.get("empNm");
		} else {
			map.put("result", "fail");
			map.put("message", "empNm not exist.");
			return map;
		}

		if (formData.containsKey("permId")) {
			permId = Integer.parseInt(formData.get("permId"));
		} else {
			map.put("result", "fail");
			map.put("message", "permId not exist.");
			return map;
		}

		if (formData.containsKey("brcd")) {
			brCd = formData.get("brcd");
		} else {
			map.put("result", "fail");
			map.put("message", "brcd not exist.");
			return map;
		}

		if (formData.containsKey("brnNm")) {
			brnNm = formData.get("brnNm");
		} else {
			map.put("result", "fail");
			map.put("message", "brnNm not exist.");
			return map;
		}

		if (formData.containsKey("uzYn")) {
			uzYn = formData.get("uzYn");
		} else {
			map.put("result", "fail");
			map.put("message", "uzYn not exist.");
			return map;
		}

		if (formData.containsKey("pstNm")) {
			pstNm = formData.get("pstNm");
		} else {
			map.put("result", "fail");
			map.put("message", "pstNm not exist.");
			return map;
		}

		String encodedPassword = env.getProperty("initial.password");

		UserMgmtDto userDto = new UserMgmtDto();
		userDto.setEmpNo(empNo);
		userDto.setPassword(encodedPassword);
		userDto.setEmpNm(empNm);
		userDto.setPermId(permId);
		userDto.setBrcd(brCd);
		userDto.setBrnNm(brnNm);
		userDto.setPstNm(pstNm);
		userDto.setUzYn(uzYn);

		userMgmtService.insert(userDto);

		map.put("result", "success");

		return map;
	}

	/**
	 * 사용자 정보 수정 팝업화면 저장 클릭
	 * 
	 * @param formData
	 * @return
	 */
	@PostMapping(value = "/update")
	@Secured("ROLE_C02_EDIT")
	public HashMap<String, Object> userUpdate(@RequestParam Map<String, String> formData) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		logger.debug("[/info/userAdmin/update] formData.toString() : {}", formData.toString());

		String empNo = null;
		int permId = 0;
		String brCd = null;
		String brnNm = null;
		String pstNm = null;
		String uzYn = null;

		if (formData.containsKey("empNo")) {
			empNo = formData.get("empNo");

			UserMgmtDto dto = userMgmtService.selectByEmpNo(empNo);

			if (dto == null) {
				map.put("result", "fail");
				map.put("message", "사용자가 존재하지 않습니다.");

				return map;
			}

		} else {
			map.put("result", "fail");
			map.put("message", "empNo not exist.");
			return map;
		}

		if (formData.containsKey("permId")) {
			permId = Integer.parseInt(formData.get("permId"));
		} else {
			map.put("result", "fail");
			map.put("message", "permId not exist.");
			return map;
		}

		if (formData.containsKey("brcd")) {
			brCd = formData.get("brcd");
		} else {
			map.put("result", "fail");
			map.put("message", "brCd not exist.");
			return map;
		}

		if (formData.containsKey("brnNm")) {
			brnNm = formData.get("brnNm");
		} else {
			map.put("result", "fail");
			map.put("message", "brnNm not exist.");
			return map;
		}

		if (formData.containsKey("uzYn")) {
			uzYn = formData.get("uzYn");
		} else {
			map.put("result", "fail");
			map.put("message", "uzYn not exist.");
			return map;
		}

		if (formData.containsKey("pstNm")) {
			pstNm = formData.get("pstNm");
		} else {
			map.put("result", "fail");
			map.put("message", "pstNm not exist.");
			return map;
		}

		UserMgmtDto userDto = userMgmtService.selectByEmpNo(empNo);
		userDto.setPermId(permId);
		userDto.setBrcd(brCd);
		userDto.setBrnNm(brnNm);
		userDto.setPstNm(pstNm);
		userDto.setUzYn(uzYn);

		userMgmtService.update(userDto);

		map.put("result", "success");

		return map;
	}

	/**
	 * 사용자 관리 삭제 팝업에서 "예" 클릭시 실행
	 * 
	 * @param empNo
	 * @return
	 */
	@PostMapping(value = "/delete")
	@Secured("ROLE_C02_DEL")
	public HashMap<String, Object> userDelete(@RequestParam("empNo") String empNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		logger.debug("[/info/userAdmin/delete] empNo.toString() : {}", empNo);

		userMgmtService.delete(empNo);

		map.put("result", "success");

		return map;
	}

	/**
	 * 패스워드 초기화
	 */
	@PostMapping(value = "/password/initialize")
	public HashMap<String, Object> initializePassword(@RequestParam("empno") List<String> empnoList) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		if (empnoList != null && empnoList.size() > 0) {
			int result = userMgmtService.initializePassword(empnoList);
			if (result > 0) {
				map.put("result", "success");
			} else {
				map.put("result", "fail");
			}
		}

		return map;
	}

	/**
	 * 신용회복위원회 직원 목록 조회.
	 * 
	 * @param departmentCode 부서코드
	 */
	@PostMapping(value = "/employee/list")
	public List<HashMap<String, String>> requestPostEmployeeInfo(
			@RequestParam("departmentCode") String departmentCode) {

		String hostname = "";

		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error("get hostname error", e);
		}

		String url = env.getProperty("ccrs.employee.search.url" + "_" + hostname);

		HttpPost httpPost = new HttpPost(url);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("departmentCode", departmentCode));

			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			String response = EntityUtils.toString(httpClient.execute(httpPost).getEntity());

			ObjectMapper mapper = new ObjectMapper();

			List<HashMap<String, String>> list = mapper.readValue(response,
					new TypeReference<List<HashMap<String, String>>>() {
					});

			return list;
		} catch (IOException e) {
			logger.error("IOException", e);
		}

		return null;
	}

}