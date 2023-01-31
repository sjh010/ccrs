package com.mobileleader.edoc.monitoring.service;


import java.util.List;

import com.mobileleader.edoc.monitoring.db.dto.PermMgmtDto;

public interface PermMgmtService {
	public List<PermMgmtDto> getPermList();
	public PermMgmtDto getPermData(int permId);
	
	public int addPerm(String permNm);	
	public int updatePerm(PermMgmtDto dto);
	public int deletePerm(int permId);		
}
