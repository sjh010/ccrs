package com.mobileleader.image.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Ecm Property 
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.10.08
 *
 */
@Repository
public class EcmProperties {
	
	@Value("#{ecmConfig['server.ip']}")
	private String serverIp;
	
	@Value("#{ecmConfig['server.port']}")
	private String serverPort;
	
	@Value("#{ecmConfig['server.id']}")
	private String serverId;
	
	@Value("#{ecmConfig['server.pw']}")
	private String serverPw;
	

	public String getServerIp() {
		return serverIp;
	}

	public int getServerPort() {
		return Integer.parseInt(serverPort);
	}

	public String getServerId() {
		return serverId;
	}

	public String getServerPw() {
		return serverPw;
	}


	@Override
	public String toString() {
		return "EcmProperty [serverIp=" + serverIp + ", serverPort=" + serverPort + ", serverId=" + serverId
				+ ", serverPw=" + "**********" + "]";
	}
	

}
