package com.mobileleader.edoc.monitoring.common.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1328951906463693182L;
	
	private String name;
	
	public Role(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

}
