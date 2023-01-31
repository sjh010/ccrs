package com.mobileleader.edoc.monitoring.service;

import java.util.HashMap;

public interface FileAttachService {
	
	public HashMap<String, Object> downloadPdf(String fileId, String docTitle);
}
