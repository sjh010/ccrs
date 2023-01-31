package client.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.FileCopyUtils;

import com.mobileleader.edoc.util.JsonUtils;

//httpClient이용
public class UserHttpClient {
	
	public String messageTrans(String url, Object request) {
		return messageTrans(url, JsonUtils.toJson(request));
	}
	
	public static String messageTrans(String url, String jsonBody) {		
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse httpRes = null;
			
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json");	// body 컨텐츠타입 (post에서만 전달)
			post.setHeader("Cache-Control", "no-cache");
			post.setHeader("Pragma", "no-cache");
			post.setHeader("Accept", "application/json");		// 요청에 대한 응답이 가능한 Context-Type
			post.setHeader("Accept-Charset", "Utf-8");
			post.setHeader("User-Agent", "사용자 브라우저입력");
			
			HttpEntity httpEntity = new StringEntity(jsonBody, "UTF-8");
			post.setEntity(httpEntity);
				
			System.out.println("Request headers");
			for(Header header : post.getAllHeaders()) {
				System.out.println("\t" + header.getName() + " : " + header.getValue());
			}
				
			httpRes = client.execute(post);
			
			return readHttpResponse(httpRes);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String upload(String url, Object request, String filePath) {
		return upload(url, JsonUtils.toJson(request), filePath);
	}
	
	public static String upload(String url, String dataStr, String filePath) {
		
		try {
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			
			StringBody dataBody = new StringBody(dataStr, ContentType.APPLICATION_JSON);
			multipartEntityBuilder.addPart("data", dataBody);
				
			// file data
			// 파일암호화 처리
			File file = new File(filePath);
			FileBody fileBody = new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, URLEncoder.encode(file.getName(),"UTF-8"));
			
			multipartEntityBuilder.addPart("file", fileBody);

			HttpPost post = new HttpPost(url);
			post.setEntity(multipartEntityBuilder.build());
			
			System.out.println("Request headers");
			for(Header header : post.getAllHeaders()) {
				System.out.println("\t" + header.getName() + " : " + header.getValue());
			}
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse httpRes = httpClient.execute(post);

			return readHttpResponse(httpRes);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String upload(String url, String jsonData, byte[] fileBuffer) throws Exception {
		try{
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			
			StringBody dataBody = new StringBody(jsonData, ContentType.APPLICATION_JSON);
			multipartEntityBuilder.addPart("data", dataBody);
			
			ByteArrayBody fileBufferBody = new ByteArrayBody(fileBuffer, ContentType.APPLICATION_OCTET_STREAM, "file");
			multipartEntityBuilder.addPart("file", fileBufferBody);
			
			HttpPost post = new HttpPost(url);
			post.setEntity(multipartEntityBuilder.build());
			
			System.out.println("Request headers");
			for(Header header : post.getAllHeaders()) {
				System.out.println("\t" + header.getName() + " : " + header.getValue());
			}
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse httpRes = httpClient.execute(post);

			return readHttpResponse(httpRes);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	

	public static String download(String url, Object request, String filePath) {
		return download(url, JsonUtils.toJson(request), filePath);
	}
	
	public static String download(String url, String jsonBody, String filePath) {
		
		String fileName = "";

		try {
			CloseableHttpClient client = HttpClients.createDefault();
			
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json");	// body 컨텐츠타입 (post에서만 전달)

			HttpEntity httpEntity = new StringEntity(jsonBody, "UTF-8");
			post.setEntity(httpEntity);
			
			System.out.println("Request headers");
			for(Header header : post.getAllHeaders()) {
				System.out.println("\t" + header.getName() + " : " + header.getValue());
			}
			
			CloseableHttpResponse httpRes = client.execute(post);
			
			for (Header header : httpRes.getHeaders("Content-Disposition")) {
				String str = header.getValue();
				int i = str.indexOf("filename=");
				if (i >= 0) {
					int j = str.indexOf("\"", i+10);
					fileName = str.substring(i+10, j);
					break;
				}				
			}
			System.out.println("[fileName = " + fileName + "]");

			byte[] fileBuffer = FileCopyUtils.copyToByteArray(httpRes.getEntity().getContent());
			
			File targetFile = new File(filePath, fileName);
			
			FileCopyUtils.copy(fileBuffer, targetFile);

			httpRes.close();
			
			return targetFile.getAbsolutePath();

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String readHttpResponse(HttpResponse httpRes) throws IllegalStateException, IOException {
		StringBuffer resStr = new StringBuffer();
		
		System.out.println("Response headers");
		for(Header header : httpRes.getAllHeaders()) {
			System.out.println("\t" + header.getName() + " = " + header.getValue());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(httpRes.getEntity().getContent(), Charset.forName("UTF-8")));
		
		String buffer = "";
		while((buffer = br.readLine()) != null) {
			resStr.append(buffer).append("\r\n");
		}
		
		System.out.println("resStr = \n" + resStr);
		
		return resStr.toString();
	}
}
