package com.mobileleader.edoc.monitoring.db.mapper;

import java.util.List;

import com.mobileleader.edoc.monitoring.db.dto.MenuAccsPermDto;
import com.mobileleader.edoc.monitoring.db.dto.PermMgmtDto;

public interface PermMapper {
	public List<PermMgmtDto> selectAll();
	public PermMgmtDto selectByPermId(int permId);
	
	public int selectPernNmCount(String permNm);
	
	public int insertPerm(PermMgmtDto dto);
	public int updatePerm(PermMgmtDto dto);
	public int deletePerm(int permId);
	
	public int insertMeneAccs(MenuAccsPermDto dto);
	public int updateMeneAccs(MenuAccsPermDto dto);
	public int deleteMeneAccsByPermId(int perId);
	public int deleteMeneAccsByMenuCd(String menuCd);
}
