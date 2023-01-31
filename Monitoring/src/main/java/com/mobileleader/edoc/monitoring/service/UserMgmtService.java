package com.mobileleader.edoc.monitoring.service;

import java.util.List;

import com.mobileleader.edoc.monitoring.common.form.UserListForm;
import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;

public interface UserMgmtService {
	public int getUserTotalCount(UserListForm form);
	public List<UserMgmtDto> getUserList(UserListForm form);
	
	public UserMgmtDto selectByEmpNo(String empNo);
	public int insert(UserMgmtDto dto);
	public int update(UserMgmtDto dto);
	public int delete(String empNo);
	
	public int initializePassword(List<String> empnoList);
}
