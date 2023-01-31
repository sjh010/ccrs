package com.inzisoft.pdf2image.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inzisoft.pdf2image.InziPDF;

/**
 * PDF to Image
 * 
 * @author bkcho@mobileleader.com
 * @since 2019.09.19
 * 
 */
@Controller
@RequestMapping(value = "/pdf2img")
public class Pdf2ImageController {
	private static final Logger logger = LoggerFactory.getLogger(Pdf2ImageController.class);

	/**
	 * PDF 페이지 수 구하기.
	 * 
	 * 	http://localhost:8080/image/pdf2img/pageCount?path=/tmp/test.pdf
	 *	http://ppcr-dr.ccrs.or.kr:7204/pdf2img/pageCount?path=/tmp/test.pdf
	 *
	 * @param req
	 * @param res
	 * @param str
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="pageCount", method={RequestMethod.GET})
	@ResponseBody
	public String cryptoEnc(HttpServletRequest req, HttpServletResponse res, @RequestParam("path")String path) throws Exception {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/pdf2img/pageCount/path");
		logger.info("Request:\r\n" + path);
		
		int pageCnt = InziPDF.getPDFPageCountEx(path);
		logger.debug("pdf count: " + pageCnt);
		 
		return pageCnt+"";
	}
	
}
