package com.inzisoft.crypto.servlet;


import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.inzisoft.crypto.properties.ServiceProperty;
import com.inzisoft.util.AESCrypto;
import com.inzisoft.util.InziCryptoException;


public class ServerContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ServerContextListener.class);
	
	@Autowired
	private ServiceProperty serviceProperty;
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		AESCrypto.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		//ServletContextListener에서 @Autowired를 사용할 수 있도록 추가
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        //if (!NetworkUtil.isWindows()) 
        {
        	 sce.getServletContext().setAttribute("startTime", new Date());
     		try {
     	 
     			String licenseFile = serviceProperty.getLicenseFile();
     			String keyFile = serviceProperty.getKeyFile();
     			String jniFile = serviceProperty.getJniFile();
     			String cryptoFile = serviceProperty.getCryptoFile();
     			
     			logger.info("licenseFile : " + licenseFile);
     			logger.info("keyFile : " + keyFile);
     			logger.info("jniFile : " + jniFile);
     			logger.info("cryptoFile : " + cryptoFile);
     			
     			AESCrypto.init(cryptoFile, jniFile, licenseFile, keyFile);
     			logger.info("inziCrypto success!!");
     			
     		} catch (InziCryptoException e) {
     			logger.error(String.format("Error in InziCrypto.init : [%d] %s", e.getErrorCode(), e.getMessage()));
     		} catch (Exception e) {
     			logger.error("Error in InziCrypto.init : " + e.getMessage());
     		}
        }
       
	}
}
