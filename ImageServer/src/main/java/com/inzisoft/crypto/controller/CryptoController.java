package com.inzisoft.crypto.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inzisoft.crypto.util.base64.Base64;
import com.inzisoft.util.AESCrypto;
import com.inzisoft.util.InziCryptoException;


/**
 * 암호화 API
 * 
 * @author bkcho@mobileleader.com
 * @since 2019.07.08
 *
 *
 */
@Controller
@RequestMapping(value = "/crypto")
public class CryptoController {
	private static final Logger logger = LoggerFactory.getLogger(CryptoController.class);

	// http://localhost:8080/image/crypto/enc?str=암호화할값123abc
	// http://ppcr.dr.ccrs.or.kr:7204/crypto/enc?str=암호화할값123abc
	@RequestMapping(value="enc", method={RequestMethod.GET})
	@ResponseBody
	public String cryptoEnc(HttpServletRequest req, HttpServletResponse res, @RequestParam("str")String str) throws Exception {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/crypto/enc...");
		logger.info("Request:\r\n" + str);
		
		byte[] enc = AESCrypto.Encrypt(str.getBytes(), false);
		String encStr = Base64.encodeBase64URLSafeString(enc);
		logger.debug("encoded: " + encStr);
		return encStr;
	}
	
	// http://localhost:8080/image/crypto/dec?str=aW56aXNjdjIMAAAABwAAAAIAIAAAAP-azY_AlixOzlZKcTvDfpJYtPhMkX5OEjkGPm2a7X5tAQAQAAAA-KqP1XOb_SXZnZi52sIg0gMABAAAAAsAAAAEAAQAAAB-AAAABQAEAAAAIAAAAAYABAAAAE4MfpoHAAQAAAC9PnBN1dzfSThNyIRbpFYBdWCqLVU0uyvfsGlLdEamPcY8gIY
	// http://ppcr.dr.ccrs.or.kr:7204/crypto/dec?str=aW56aXNjdjIMAAAABwAAAAIAIAAAANczjiYJpPkaLGXPmaNdENDqh3b7ICFJih0hYKCbanyrAQAQAAAA48vU0Suh7mDQhJzVjOt5iwMABAAAAAsAAAAEAAQAAAB-AAAABQAEAAAAIAAAAAYABAAAAFDWC5oHAAQAAAC9PnBNjWfdu1nkwP_1gskAelz5OcMTuFgLZ1h119PvKgsfCrQ
	@RequestMapping(value="dec", method={RequestMethod.GET})
	@ResponseBody
	public String cryptoDec(HttpServletRequest req, HttpServletResponse res, @RequestParam("str")String str) throws Exception {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/crypto/dec..");
		logger.info("Request:\r\n" + str);

		try {
			byte[] decBase64 = Base64.decodeBase64(str);
			byte[] dec = AESCrypto.Decrypt(decBase64);
			String decStr = new String(dec);
			logger.info(decStr);
			return new String(decStr.getBytes("UTF-8"), "iso-8859-1");
			
		} catch (InziCryptoException e) {
			return null;
		}
	}
}
