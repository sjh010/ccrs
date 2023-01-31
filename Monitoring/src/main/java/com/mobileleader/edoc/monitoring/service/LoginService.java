package com.mobileleader.edoc.monitoring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobileleader.edoc.monitoring.common.form.PasswordModifyForm;
import com.mobileleader.edoc.monitoring.common.model.LoginInfo;
import com.mobileleader.edoc.monitoring.common.model.Role;
import com.mobileleader.edoc.monitoring.db.dto.MenuAccsPermDto;
import com.mobileleader.edoc.monitoring.db.dto.PermMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;
import com.mobileleader.edoc.monitoring.db.mapper.PermMapper;
import com.mobileleader.edoc.monitoring.db.mapper.UserMgmtMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

	private final UserMgmtMapper userMgmtMapper;

	private final PermMapper permMapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginInfo loadUserByUsername(String username) throws UsernameNotFoundException {

		UserMgmtDto userMgmtDto = userMgmtMapper.selectByEmpNo(username);

		if (userMgmtDto == null) {
			throw new UsernameNotFoundException(username + " : not found.");
		}

		int permId = userMgmtDto.getPermId();

		PermMgmtDto permMgmtDto = permMapper.selectByPermId(permId);

		if (permMgmtDto == null) {
			// TODO throw no permission exception.
		}

		List<Role> authorities = new ArrayList<Role>();

		List<MenuAccsPermDto> menuAccsPermList = permMgmtDto.getMenuAccsPerm();

		if (menuAccsPermList != null && !menuAccsPermList.isEmpty()) {

			for (MenuAccsPermDto dto : menuAccsPermList) {
				List<Role> menuAuthorities = convertAuthorities(dto);

				if (menuAuthorities != null && !menuAuthorities.isEmpty()) {
					authorities.addAll(menuAuthorities);
				}
			}
		}

		LoginInfo loginInfo = new LoginInfo(username, userMgmtDto.getPassword(), authorities, userMgmtDto);

		return loginInfo;
	}

	private List<Role> convertAuthorities(MenuAccsPermDto dto) {
		String menuCd = dto.getMenuCd();
		List<Role> menuAuthorities = new ArrayList<Role>();

		if (dto.getAccsYn().equalsIgnoreCase("Y")) {
			menuAuthorities.add(new Role("ROLE_" + menuCd + "_ACCS"));
		}

		if (dto.getListYn().equalsIgnoreCase("Y")) {
			menuAuthorities.add(new Role("ROLE_" + menuCd + "_LIST"));
		}

		if (dto.getDtalYn().equalsIgnoreCase("Y")) {
			menuAuthorities.add(new Role("ROLE_" + menuCd + "_DTAL"));
		}

		if (dto.getRegYn().equalsIgnoreCase("Y")) {
			menuAuthorities.add(new Role("ROLE_" + menuCd + "_REG"));
		}

		if (dto.getEditYn().equalsIgnoreCase("Y")) {
			menuAuthorities.add(new Role("ROLE_" + menuCd + "_EDIT"));
		}

		if (dto.getDelYn().equalsIgnoreCase("Y")) {
			menuAuthorities.add(new Role("ROLE_" + menuCd + "_DEL"));
		}

		return menuAuthorities;
	}

	public Map<String, Object> modifyPassword(PasswordModifyForm passwordModifyForm) {
		Map<String, Object> map = new HashMap<String, Object>();

		String empNo = SecurityContextHolder.getContext().getAuthentication().getName();
		UserMgmtDto userMgmtDto = userMgmtMapper.selectByEmpNo(empNo);

		if (userMgmtDto != null) {
			// 현재 비밀번호 체크
			if (!passwordEncoder.matches(passwordModifyForm.getCurrentPassword(), userMgmtDto.getPassword())) {
				map.put("result", "invalidPassword");
			} else {
				String encodedNewPasswordParameter = passwordEncoder.encode(passwordModifyForm.getNewPassword());
				userMgmtDto.setPassword(encodedNewPasswordParameter);
				int result = userMgmtMapper.update(userMgmtDto);
				if (result > 0) {
					map.put("result", "success");
				} else {
					map.put("result", "fail");
				}
			}
		} else {
			map.put("result", "fail");
		}

		return map;
	}
}
