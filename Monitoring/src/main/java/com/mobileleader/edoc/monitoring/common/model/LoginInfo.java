package com.mobileleader.edoc.monitoring.common.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;

public class LoginInfo extends User {

	private static final long serialVersionUID = -3979097509334783453L;

	private UserMgmtDto userMgmtDto;
	
	public LoginInfo(String username, String password,
			Collection<? extends GrantedAuthority> authorities, UserMgmtDto userMgmtDto) {
		super(username, password, authorities);
		this.userMgmtDto = userMgmtDto;
	}

	public UserMgmtDto getUserMgmtDto() {
		return userMgmtDto;
	}

	public void setUserMgmtDto(UserMgmtDto userMgmtDto) {
		this.userMgmtDto = userMgmtDto;
	}
}
