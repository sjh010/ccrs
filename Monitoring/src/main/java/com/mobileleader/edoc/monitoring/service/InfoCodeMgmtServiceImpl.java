package com.mobileleader.edoc.monitoring.service;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mobileleader.edoc.monitoring.common.form.InfoCodeForm;
import com.mobileleader.edoc.monitoring.common.model.LoginInfo;
import com.mobileleader.edoc.monitoring.db.dto.InfoCodeDto;
import com.mobileleader.edoc.monitoring.db.mapper.InfoCodeMgmtMapper;

@Service
public class InfoCodeMgmtServiceImpl implements InfoCodeMgmtService {
	
	@Autowired
	private InfoCodeMgmtMapper infoCodeMgmtMapper;

	@Override
	@Transactional
	public int getTotalCount(InfoCodeForm infoCodeForm) throws Exception{
		return infoCodeMgmtMapper.selectTotalCount(infoCodeForm);
	}
	
	@Override
	@Transactional
	public List<InfoCodeDto> getCodeList(InfoCodeForm infoCodeForm) throws Exception{
		//TODO 이 로직은 공통으로 사용하기 때문에 ServiceImpl이 아닌 BaseForm안에서 처리를 하는 것이 맞으나 다른 파트 개발에 영향이 있어
		//논의 후 공통으로 처리할 필요가 있어 보임. 그 전 까지는 본 메뉴에만 처리 로직 추가
		int pageNo = infoCodeForm.getPageNo();
		int pageSize = infoCodeForm.getPageSize();
		infoCodeForm.setPageStartIndex((pageNo - 1) * pageSize);
		infoCodeForm.setPageEndIndex(pageNo * pageSize);
		
		return infoCodeMgmtMapper.selectCodeList(infoCodeForm);
	}

	@Override
	@Transactional
	public int chkDupCode(InfoCodeForm infoCodeForm) throws Exception{
		int result = 0;
		
		List<InfoCodeDto> codeGroups = infoCodeMgmtMapper.selectGroupCodeList(infoCodeForm.getCdGroup());
		
		if(codeGroups != null && codeGroups.size() > 0){
			String cdGroup = infoCodeForm.getCdGroup();
			String exCdGroup = infoCodeForm.getExCdGroup();
			String cdVal = infoCodeForm.getCdVal();
			String exCdVal = infoCodeForm.getExCdVal();
			String cdNm = infoCodeForm.getCdNm();
			String seqno = infoCodeForm.getSeqno();
			boolean sameKeyYn = false;
			
			//코드 수정일때 코드명 또는 순서만 변경할 경우에 대한 처리
			if((!StringUtils.isEmpty(exCdGroup) && cdGroup.equals(exCdGroup))
					&& (!StringUtils.isEmpty(exCdVal) && cdVal.equals(exCdVal))){
				sameKeyYn = true;
			}
			
			for(InfoCodeDto dto : codeGroups){
				if(cdVal.equals(dto.getCdVal())){		//동일 코드 그룹내에서 코드 값이 중복될 경우
					if(!sameKeyYn){
						result = -1;
						break;
					}
				}else if(cdNm.equals(dto.getCdNm())){	//동일 코드 그룹내에서 코드명이 중복될 경우
					result = -2;
					break;
				}else if(seqno.equals(dto.getSeqno())){	//동일 코드 그룹내에서 순서가 중복될 경우
					result = -3;
					break;
				}
			}
		}
		return result;
	}
	
	@Override
	@Transactional
	public int registCode(InfoCodeForm infoCodeForm) throws Exception{
		InfoCodeDto infoCodeDto = new InfoCodeDto();
		BeanUtils.copyProperties(infoCodeDto, infoCodeForm);
		
		LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		infoCodeDto.setRegBrcd(loginInfo.getUserMgmtDto().getBrcd());
		infoCodeDto.setRegBrnNm(loginInfo.getUserMgmtDto().getBrnNm());
		infoCodeDto.setRegEmpno(loginInfo.getUserMgmtDto().getEmpNo());
		infoCodeDto.setRegEmpNm(loginInfo.getUserMgmtDto().getEmpNm());
		
		return infoCodeMgmtMapper.insertCode(infoCodeDto);
	}
	
	@Override
	@Transactional
	public int modifyCode(InfoCodeForm infoCodeForm) throws Exception{
		InfoCodeDto infoCodeDto = new InfoCodeDto();
		BeanUtils.copyProperties(infoCodeDto, infoCodeForm);
		
		LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		infoCodeDto.setRegBrcd(loginInfo.getUserMgmtDto().getBrcd());
		infoCodeDto.setRegBrnNm(loginInfo.getUserMgmtDto().getBrnNm());
		infoCodeDto.setRegEmpno(loginInfo.getUserMgmtDto().getEmpNo());
		infoCodeDto.setRegEmpNm(loginInfo.getUserMgmtDto().getEmpNm());
		
		return infoCodeMgmtMapper.updateCode(infoCodeDto);
	}
	
	@Override
	@Transactional
	public int deleteCode(InfoCodeForm infoCodeForm) throws Exception{
		return infoCodeMgmtMapper.deleteCode(infoCodeForm);
	}

	@Override
	@Transactional
	public List<InfoCodeDto> getGroupCodeList(String groupCode) {
		return infoCodeMgmtMapper.selectGroupCodeList(groupCode);
	}
	
	@Override
	@Transactional
	public InfoCodeDto getGroupCode(String groupCode, String code) {
		return infoCodeMgmtMapper.selectGroupCode(groupCode, code);
	}
	
}
