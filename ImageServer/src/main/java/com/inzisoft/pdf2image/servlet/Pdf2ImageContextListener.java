package com.inzisoft.pdf2image.servlet;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.inzisoft.pdf2image.InziPDF;
import com.inzisoft.pdf2image.properties.Pdf2ImageProperty;

/**
 * PDF 2 Image 모듈을 사용하기 위해 was 구동시  라이브러리를 로딩한다.
 * @author mobileleader
 *
 */
public class Pdf2ImageContextListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(Pdf2ImageContextListener.class);

	@Autowired
	Pdf2ImageProperty pdf2ImageProperty;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("-------------------------------- Pdf2ImageContextListener ---------------------------------");
		
		//ServletContextListener에서 @Autowired를 사용할 수 있도록 추가
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	
//        if (!NetworkUtil.isWindows()) 
        {
        	sce.getServletContext().setAttribute("startTime", new Date());
 	 
 			String licenseFile = pdf2ImageProperty.getLicenseFile();
 			String jniFile = pdf2ImageProperty.getSoFile();
 			int result = InziPDF.init(jniFile, licenseFile);
 			if (result == 1) {
 				logger.info("pdf2image library load success!!");
 			}
 			else {
 				logger.error("pdf2image library load failed...........");
 			}
        }
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
