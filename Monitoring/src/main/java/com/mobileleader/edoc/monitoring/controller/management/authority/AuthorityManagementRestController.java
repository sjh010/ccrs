package com.mobileleader.edoc.monitoring.controller.management.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mobileleader.edoc.monitoring.db.dto.MenuAccsPermDto;
import com.mobileleader.edoc.monitoring.db.dto.PermMgmtDto;
import com.mobileleader.edoc.monitoring.service.PermMgmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/info/authAdmin")
@RequiredArgsConstructor
@Slf4j
public class AuthorityManagementRestController {

	private final PermMgmtService permMgmtService;

	@PostMapping(value = "/list")
	@Secured("ROLE_C03_LIST")
	public HashMap<String, Object> userList(@RequestParam("permId") int permId) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		logger.info("[/info/authAdmin/list] permId : {}", permId);

		PermMgmtDto dto = permMgmtService.getPermData(permId);

		List<MenuAccsPermDto> list = dto.getMenuAccsPerm();

		if (list == null) {
			list = new ArrayList<MenuAccsPermDto>();
		}
		int totalCount = list != null ? list.size() : 0;

		map.put("list", list);

		map.put("totalCount", totalCount);
		map.put("result", "success");

		return map;
	}

	@PostMapping(value = "/add")
	@Secured("ROLE_C03_REG")
	public HashMap<String, Object> addPerm(@RequestParam("permNm") String permNm) {
		logger.info("/info/authAdmin/add] permNm : {} ", permNm);

		HashMap<String, Object> map = new HashMap<String, Object>();

		int result = permMgmtService.addPerm(permNm);

		if (result > 0) {
			map.put("permId", result);
			map.put("permNm", permNm);
			map.put("result", "success");
		} else if (result == -1) {
			map.put("result", "duplicate");
		} else {
			logger.error("addPerm fail.");
			map.put("result", "fail");
		}

		return map;
	}

	@PostMapping(value = "/rename")
	@Secured("ROLE_C03_EDIT")
	public HashMap<String, Object> renamePerm(@RequestParam("permId") int permId,
			@RequestParam("permNm") String permNm) {
		logger.info("/info/authAdmin/rename] permId : {}, permNm : {} ", permId, permNm);

		HashMap<String, Object> map = new HashMap<String, Object>();

		if (permId < 10) {
			map.put("result", "fail");
			map.put("message", "변경 불가.");
			return map;
		}

		PermMgmtDto dto = new PermMgmtDto();
		dto.setPermId(permId);
		dto.setPermNm(permNm);

		int result = permMgmtService.updatePerm(dto);

		if (result > 0) {
			map.put("result", "success");
		} else if (result == -1) {
			map.put("result", "duplicate");
		} else {
			logger.error("addPerm fail.");
			map.put("result", "fail");
		}

		return map;
	}

	@PostMapping(value = "/delete")
	@Secured("ROLE_C03_DEL")
	public HashMap<String, Object> deletePerm(@RequestParam("permId") int permId) {
		logger.info("/info/authAdmin/delete] permId : {} ", permId);

		HashMap<String, Object> map = new HashMap<String, Object>();

		if (permId < 10) {
			map.put("result", "fail");
			map.put("message", "변경 불가.");
			return map;
		}

		int result = permMgmtService.deletePerm(permId);

		if (result > 0) {
			map.put("result", "success");
		} else {
			logger.error("addPerm fail.");
			map.put("result", "fail");
		}

		return map;
	}

	@PostMapping(value = "/update")
	@Secured("ROLE_C03_EDIT")
	public HashMap<String, Object> updateMenuAccs(@RequestParam("permId") int permId,
			@RequestParam Map<String, String> formData) {
		logger.info("/info/authAdmin/update] permId : {} ", formData);

		HashMap<String, Object> map = new HashMap<String, Object>();

		PermMgmtDto dto = permMgmtService.getPermData(permId);

		List<MenuAccsPermDto> menuAccsPermDtoList = dto.getMenuAccsPerm();

		for (MenuAccsPermDto menuAccsPermDto : menuAccsPermDtoList) {
			String menuCd = menuAccsPermDto.getMenuCd();

			if (formData.containsKey(menuCd + "_accsYn") && formData.get(menuCd + "_accsYn").equalsIgnoreCase("on")) {
				menuAccsPermDto.setAccsYn("Y");
			} else {
				menuAccsPermDto.setAccsYn("N");
			}
			if (formData.containsKey(menuCd + "_listYn") && formData.get(menuCd + "_listYn").equalsIgnoreCase("on")) {
				menuAccsPermDto.setListYn("Y");
			} else {
				menuAccsPermDto.setListYn("N");
			}
			if (formData.containsKey(menuCd + "_dtalYn") && formData.get(menuCd + "_dtalYn").equalsIgnoreCase("on")) {
				menuAccsPermDto.setDtalYn("Y");
			} else {
				menuAccsPermDto.setDtalYn("N");
			}
			if (formData.containsKey(menuCd + "_regYn") && formData.get(menuCd + "_regYn").equalsIgnoreCase("on")) {
				menuAccsPermDto.setRegYn("Y");
			} else {
				menuAccsPermDto.setRegYn("N");
			}
			if (formData.containsKey(menuCd + "_editYn") && formData.get(menuCd + "_editYn").equalsIgnoreCase("on")) {
				menuAccsPermDto.setEditYn("Y");
			} else {
				menuAccsPermDto.setEditYn("N");
			}
			if (formData.containsKey(menuCd + "_delYn") && formData.get(menuCd + "_delYn").equalsIgnoreCase("on")) {
				menuAccsPermDto.setDelYn("Y");
			} else {
				menuAccsPermDto.setDelYn("N");
			}

		}

		int result = permMgmtService.updatePerm(dto);

		if (result >= 0) {
			map.put("result", "success");
		} else {
			logger.error("addPerm fail.");
			map.put("result", "fail");
		}

		return map;
	}

}