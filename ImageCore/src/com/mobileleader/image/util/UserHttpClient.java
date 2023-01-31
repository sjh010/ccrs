package com.mobileleader.image.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.image.client.Exception.UserHttpStatus;
import com.mobileleader.image.server.model.response.BaseResponse;
import com.mobileleader.image.server.model.response.DownloadResponse;
 
/**
 * 서버와 통신을 위해 정의됨.
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 *
 */
public class UserHttpClient {
	private static final Logger logger = LoggerFactory.getLogger(UserHttpClient.class);
	
	/**
	 * Message 통신
	 * 
	 * @param url
	 * @param jsonBody
	 * @return
	 */
	public String messageTrans(String url, String jsonBody){
		logger.debug("messageTrans");
		
		String ret = null;
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse httpRes = null;
		
		try {
			if (jsonBody == null) {
				logger.debug("get");
				HttpGet get = new HttpGet(url);
				get.setHeader("Cache-Control", "no-cache");
				get.setHeader("Pragma", "no-cache");
				httpRes = client.execute(get);
			}
			else {
				logger.debug("post");
				HttpPost post = new HttpPost(url);
				post.setHeader("Content-Type", "application/json");	// body 컨텐츠타입 (post에서만 전달)
				post.setHeader("Cache-Control", "no-cache");
				post.setHeader("Pragma", "no-cache");
				post.setHeader("Accept", "application/json");		// 요청에 대한 응답이 가능한 Context-Type
				post.setHeader("Accept-Charset", "UTF-8");
				post.setHeader("User-Agent", "사용자 브라우저입력");
				
				HttpEntity httpEntity = new StringEntity(jsonBody, "UTF-8");
				post.setEntity(httpEntity);
				httpRes = client.execute(post);
			}
			
			// Header
			Header[] headers = httpRes.getAllHeaders();
			for (Header header : headers) {
				logger.info(header.getName() + " : " + header.getValue());
			}
			
			// body
			HttpEntity httpEntity = httpRes.getEntity();
			
			int statusCode = httpRes.getStatusLine().getStatusCode();
			if(statusCode == UserHttpStatus.OK.getStatusCode()) {
				ret = EntityUtils.toString(httpEntity, "UTF-8");
			} else {
				BaseResponse response = new BaseResponse();
				response.setResultCode(statusCode);
				response.setResultMessage(UserHttpStatus.getByCode(statusCode).getStatusMessage());
				ret = response.toString();
			}
			
		} catch (IOException e) {
			BaseResponse response = new BaseResponse();
			response.setResultCode(UserHttpStatus.CLIENT_SERVICE_ERROR.getStatusCode());
			response.setResultMessage(e.getMessage());
			ret = response.toString();		
		} finally {
			try {
				if(httpRes != null) {
					httpRes.close();
				}
			} catch (IOException e) {
				logger.error("httpRes close Failed", e);
			}
		}
		
		return ret;
	}
	
	/**
	 * 파일 다운로드
	 * 
	 * @param url
	 * @param jsonBody
	 * 	다운로드 키값.
	 * @return
	 */
	public DownloadResponse download(String url, String jsonBody) {
		logger.debug("download");
		
		DownloadResponse res = new DownloadResponse();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse httpRes = null;
		
		try {
			if (jsonBody == null) {
				logger.debug("get");
				HttpGet get = new HttpGet(url);
				get.setHeader("Cache-Control", "no-cache");
				get.setHeader("Pragma", "no-cache");
				httpRes = client.execute(get);
			}
			else {
				logger.debug("post");
				HttpPost post = new HttpPost(url);
				post.setHeader("Content-Type", "application/json");	// body 컨텐츠타입 (post에서만 전달)
				post.setHeader("Cache-Control", "no-cache");
				post.setHeader("Pragma", "no-cache");
				post.setHeader("User-Agent", "사용자 브라우저입력");				// 값을 입력하지 않으면 서버에서 비정상적으로 처리됨.
				post.setHeader("Accept", "application/octet-stream");		// 요청에 대한 응답이 가능한 
				
				HttpEntity httpEntity = new StringEntity(jsonBody, "UTF-8");
				post.setEntity(httpEntity);
				httpRes = client.execute(post);
			}
			
			// Header
			Header[] headers = httpRes.getAllHeaders();
			for (Header header : headers) {
				if (header.getName().equals("Content-Disposition")) {
					String str = header.getValue();
					int i = str.indexOf("filename=");
					if (i < 0) {
						res.setFileName(null);
					}
					else {
						int j = str.indexOf("\"", i+10);
						res.setFileName(str.substring(i+10, j));
						break;
					}
				}				
			}
			
			int code = httpRes.getStatusLine().getStatusCode();
			String msg = httpRes.getStatusLine().getReasonPhrase();
			
			if(code != UserHttpStatus.OK.getStatusCode()) {
				res.setResultCode(code);
				res.setResultMessage(UserHttpStatus.getByCode(code).getStatusMessage());
				return res;
			}
			
			// body
			HttpEntity httpEntity = httpRes.getEntity();
			InputStream is = httpEntity.getContent();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			int byteRead = 0;
			byte[] buffer = new byte[4096];
			while ((byteRead = is.read(buffer)) != -1) {
				out.write(buffer, 0, byteRead);
			}

			res.setResultCode(code);
			res.setResultMessage(msg);			
			res.setLength(out.size());
			res.setBuffer(out.toByteArray());
			
			out.close();

		} catch (IOException e) {
			res.setResultCode(UserHttpStatus.CLIENT_SERVICE_ERROR.getStatusCode());
			res.setResultMessage(e.getMessage());

		} finally {
			try {
				if(httpRes != null) {
					httpRes.close();
				}
			} catch (IOException e) {
				logger.error("httpRes close Failed", e);
			}
		}
		return res;
	}
	
	
	/**
	 * JSON + 다중 파일 업로드
	 *  
	 * @param url
	 * @param uploadRequest
	 * @return
	 * @throws IOException 
	 */
	public String upload(String url, MultipartEntityBuilder multipartEntityBuilder) {
		logger.debug("sender");
		String ret = null;
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpRes = null;
		BufferedReader br = null;
		
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(multipartEntityBuilder.build());		
			httpRes = httpClient.execute(post);			
			
			int statusCode = httpRes.getStatusLine().getStatusCode();
			if(statusCode != UserHttpStatus.OK.getStatusCode()) {
				BaseResponse response = new BaseResponse();
				response.setResultCode(statusCode);
				response.setResultMessage(UserHttpStatus.getByCode(statusCode).getStatusMessage());
				ret = response.toString();
			}
			
			// server response parsing		
			HttpEntity httpEntity = httpRes.getEntity();
			br = new BufferedReader(new InputStreamReader(httpEntity.getContent(), Charset.forName("UTF-8")));
	 
			String buffer = "";
			StringBuffer result = new StringBuffer();
			while( (buffer = br.readLine()) != null) {
				result.append(buffer).append("\r\n");
			}

			ret = result.toString();
	 
		} catch(IOException e) {
			BaseResponse response = new BaseResponse();
			response.setResultCode(UserHttpStatus.CLIENT_SERVICE_ERROR.getStatusCode());
			response.setResultMessage(e.getMessage());
			ret = response.toString();
		} finally {
			try {
				if(br != null) br.close();
				if(httpRes != null) {
					httpRes.close();
				}
				httpClient.close();
			} catch (IOException e) {
				logger.error("httpRes close Failed", e);
			}
		}
		
		return ret;
	}
}
