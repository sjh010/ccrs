package com.mobileleader.edoc.monitoring.config.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;

import com.google.common.io.Files;
import com.mobileleader.image.client.service.DownloadService;
import com.mobileleader.image.server.model.request.DownloadRequest;
import com.mobileleader.image.server.model.response.DownloadResponse;
import com.mobileleader.webform.common.data.FileInfo;
import com.mobileleader.webform.common.exception.ServiceError;
import com.mobileleader.webform.common.exception.ServiceException;
import com.mobileleader.webform.common.storage.FileStorage;
import com.mobileleader.webform.common.storage.StorageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class EdocFileStorage implements FileStorage {

	private final Environment env;
	
	@Override
	public FileInfo getFileInfo(String fileId) throws ServiceException {

		logger.info("getFileInfo() : fileId : {}", fileId);
		File file = getFile(fileId);
		
		FileInfo fileInfo = new FileInfo();
		
		fileInfo.setFileId(fileId);
		fileInfo.setFileName(file.getName());
		fileInfo.setFileExt(FilenameUtils.getExtension(file.getName()));
		fileInfo.setFileSize(file.length());
		
		file.delete();
		
		return fileInfo;
	}
	
	@Override
	public File getFile(String fileId) throws ServiceException {
		
		String hostname = "";
		
		try {
			 hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error("get hostname error", e);
		}
		
		String instanceName = System.getProperty("weblogic.Name");
		
		StringBuilder sb = new StringBuilder("image.download");
		sb.append("_").append(hostname).append("_").append(instanceName);
		
		logger.info("Property : {}", sb.toString());
		
		String url = env.getProperty(sb.toString());
		
		logger.info("URL : {}", url);
		
		DownloadRequest req = new DownloadRequest(fileId);
		
		DownloadService downloadService = new DownloadService(false);

		DownloadResponse res = new DownloadResponse();
			
		res = downloadService.download(url, req);
		
		File file = null;
		
		logger.info("Response : resultCode : {}, resultMessage : {}", res.getResultCode(), res.getResultMessage());
		
		if (res.getResultCode() == 200) {
			try {
				String fileName = env.getProperty("image.rootPath") + File.separator + FilenameUtils.getBaseName(res.getFileName());
				
				file = new File(fileName);
				
				FileOutputStream fos = new FileOutputStream(file);
				
				fos.write(res.getBuffer());
				fos.close();
				
				return file;
			} catch (IOException e) {
				throw new ServiceException(ServiceError.FILE_IO_ERROR, e);
			}
		}
		
		return null;
	}

	@Override
	public boolean getFile(String fileId, OutputStream os) throws ServiceException {

		File file = getFile(fileId);
		
		try {
			Files.copy(file, os);
		} catch (IOException e) {
			throw new ServiceException(ServiceError.FILE_READ_FAIL, e);
		} finally {
			file.delete();
		}
		
		return true;
	}

	@Override
	public byte[] getFileBinary(String fileId) throws ServiceException {
		
		byte[] fileByteArr = null;
		
		File file = getFile(fileId);
		
		try {
			fileByteArr = Files.toByteArray(file);
		} catch (IOException e) {
			throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, e);
		} finally {
			file.delete();
		}
		
		return fileByteArr;
	}
	
	@Override
	public String addFile(File arg0, String arg1, String arg2, String arg3) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addFile(File arg0, String arg1, String arg2, String arg3, Map<String, String> arg4)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exist(String arg0) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FileStorage getLowStorage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StorageType getStorageType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLowStorage(FileStorage arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateFile(String arg0, String arg1, File arg2) throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

}
