package com.mobileleader.edoc.monitoring.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mobileleader.edoc.monitoring.common.form.UserListForm;
import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;
import com.mobileleader.edoc.monitoring.db.mapper.UserMgmtMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:properties/ccrs.properties")
public class UserMgmtServiceImpl implements UserMgmtService {

	private final UserMgmtMapper userMgmtMapper;
	
	private final Environment env;
	
	@Override
	@Transactional(readOnly=true)
	public int getUserTotalCount(UserListForm form) {
		return userMgmtMapper.count(form);
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserMgmtDto> getUserList(UserListForm form) {
		return userMgmtMapper.selectList(form);
	}

	@Override
	@Transactional(readOnly=true)
	public UserMgmtDto selectByEmpNo(String empNo) {
		return userMgmtMapper.selectByEmpNo(empNo);
	}

	@Override
	@Transactional
	public int insert(UserMgmtDto dto) {
		String empNo = SecurityContextHolder.getContext().getAuthentication().getName();
		
		dto.setRegEmpNo(empNo);
		dto.setChngEmpNo(empNo);
		
		return userMgmtMapper.insert(dto);
	}

	@Override
	@Transactional
	public int update(UserMgmtDto dto) {
		String empNo = SecurityContextHolder.getContext().getAuthentication().getName();
		
		dto.setChngEmpNo(empNo);
		
		return userMgmtMapper.update(dto);
	}

	@Override
	@Transactional
	public int delete(String empNo) {
		return userMgmtMapper.delete(empNo);
	}

	@Override
	@Transactional
	public int initializePassword(List<String> empnoList) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("empnoList", empnoList);
		
		String encodedPassword = env.getProperty("initial.password");
		param.put("password", encodedPassword);
		
		return userMgmtMapper.initializePassword(param);
	}

}
