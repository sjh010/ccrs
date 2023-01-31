package com.mobileleader.edoc.monitoring.db.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mobileleader.edoc.monitoring.common.form.UserListForm;
import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;

public interface UserMgmtMapper {

	public int count(UserListForm form);
	public List<UserMgmtDto> selectList(UserListForm form);
	
	public UserMgmtDto selectByEmpNo(String empNo);	
	
	public int insert(UserMgmtDto dto);
	public int update(UserMgmtDto dto);
	public int delete(String empNo);
	
	public int initializePassword(HashMap<String, Object> param);
	public int updatePasswordErrorCount(@Param("empNo") String empNo, @Param("passwordErrorCount") int passwordErrorCount);
}
