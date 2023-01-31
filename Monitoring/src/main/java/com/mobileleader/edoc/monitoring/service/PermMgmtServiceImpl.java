package com.mobileleader.edoc.monitoring.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobileleader.edoc.monitoring.common.form.InfoCodeForm;
import com.mobileleader.edoc.monitoring.db.dto.InfoCodeDto;
import com.mobileleader.edoc.monitoring.db.dto.MenuAccsPermDto;
import com.mobileleader.edoc.monitoring.db.dto.PermMgmtDto;
import com.mobileleader.edoc.monitoring.db.mapper.PermMapper;

@Service
public class PermMgmtServiceImpl implements PermMgmtService {

	private static final Logger logger = LoggerFactory.getLogger(PermMgmtServiceImpl.class);
	
	@Autowired
	private PermMapper permMapper;
	
	@Autowired
	private InfoCodeMgmtService codeService;
	
	@Override
	@Transactional(readOnly= true)
	public List<PermMgmtDto> getPermList() {
		return permMapper.selectAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public PermMgmtDto getPermData(int permId) {
		return permMapper.selectByPermId(permId);
	}

	@Override
	@Transactional
	public int addPerm(String permNm) {		
		logger.info("addPerm : {}", permNm);
		
		if(permMapper.selectPernNmCount(permNm)>0) {
			logger.info("addPerm fail : {} exist.", permNm);
			return -1;
		}
		
		String empNo = SecurityContextHolder.getContext().getAuthentication().getName();
		
		PermMgmtDto dto = new PermMgmtDto();
		dto.setPermNm(permNm);
		dto.setRegEmpNo(empNo);
		dto.setChngEmpNo(empNo);
		
		logger.debug("insertPerm : {}", dto.toString());
		permMapper.insertPerm(dto);
		
		List<String> menuCdList = getMenuCdList();
		
		MenuAccsPermDto menuDto = new MenuAccsPermDto();
		menuDto.setRegEmpNo(empNo);
		menuDto.setChngEmpNo(empNo);
		menuDto.setPermId(dto.getPermId());
		menuDto.setAccsYn("N");
		menuDto.setListYn("N");
		menuDto.setDtalYn("N");
		menuDto.setRegYn("N");
		menuDto.setEditYn("N");		
		menuDto.setDelYn("N");
		for(String menuCd : menuCdList) {
			menuDto.setMenuCd(menuCd);;
			
			logger.debug("insertMeneAccs : {}", menuDto.toString());
			permMapper.insertMeneAccs(menuDto);
		}
		
		
		return dto.getPermId();
	}
	
	private List<String> getMenuCdList() {
		InfoCodeForm codeForm = new InfoCodeForm();
		codeForm.setCdGroup("MENU_G");
		
		List<String> menuCdList = new ArrayList<String>();
		
		try {
			List<InfoCodeDto> dtoList = codeService.getCodeList(codeForm);
			
			if(dtoList != null && !dtoList.isEmpty()) {
				for(InfoCodeDto dto : dtoList) {
					menuCdList.add(dto.getCdVal());
				}
			}
		} catch (SQLException e) {
			logger.error("getMenuCdList error. return default set.", e);
		} catch (Exception e) {
			logger.error("getMenuCdList error. return default set.", e);
		}
		
		if(menuCdList.isEmpty()) {
		    menuCdList.add("A00");
			menuCdList.add("A01");
			menuCdList.add("A02");
			menuCdList.add("A03");
			menuCdList.add("A04");
			menuCdList.add("B01");
			menuCdList.add("C01");
			menuCdList.add("C02");
			menuCdList.add("C03");
			menuCdList.add("D01");
			menuCdList.add("D02");
			
			logger.info("set as default menuCd : ", menuCdList.toString());
		}
		
		return menuCdList;
	}

	@Override
	@Transactional
	public int updatePerm(PermMgmtDto dto) {
		
		logger.debug("updatePerm", dto.toString());
		
		int count = 0;
		String empNo = SecurityContextHolder.getContext().getAuthentication().getName();
		
		int permId = dto.getPermId();
		
		PermMgmtDto srcDto = permMapper.selectByPermId(permId);
		
		if(dto.getPermNm() != null && dto.getPermNm().compareTo(srcDto.getPermNm()) != 0) {
			
			if(permMapper.selectPernNmCount(dto.getPermNm())>0) {
				logger.info("updatePerm fail : {} exist.", dto.getPermNm());
				return -1;
			}
			
			logger.info("update {} permNm to {}", permId, dto.getPermNm());			
			
			dto.setChngEmpNo(empNo);
			
			permMapper.updatePerm(dto);
			count++;
			
		}
		
		List<MenuAccsPermDto> menuAccsPermList = dto.getMenuAccsPerm();
		
		if(menuAccsPermList != null && !menuAccsPermList.isEmpty()) {
			for(MenuAccsPermDto menuAccsDto : menuAccsPermList) {
				List<MenuAccsPermDto> srcMenuAccsPermList = srcDto.getMenuAccsPerm();
				
				for(MenuAccsPermDto srcMenuAccsDto : srcMenuAccsPermList) {
					
					if(srcMenuAccsDto.getMenuCd().compareTo(menuAccsDto.getMenuCd()) == 0) {
						if(!srcMenuAccsDto.equals(menuAccsDto)) {
							menuAccsDto.setPermId(permId);
							menuAccsDto.setChngEmpNo(empNo);

							logger.info("update menuAccsPerm : ", menuAccsDto.toString());
							
							permMapper.updateMeneAccs(menuAccsDto);
							count++;
						}
						break;
					}
				}
			}
		}
		
		return count;
	}

	@Override
	@Transactional
	public int deletePerm(int permId) {
		logger.info("deletePerm : {}", permId);
		
		int count = 0;
		count += permMapper.deletePerm(permId);
		count += permMapper.deleteMeneAccsByPermId(permId);
		
		return count;
	}
	
}
