package com.mobileleader.edoc.monitoring.controller.viewer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mobileleader.webform.common.data.DocInfo;
import com.mobileleader.webform.common.data.FileInfo;
import com.mobileleader.webform.common.exception.ServiceError;
import com.mobileleader.webform.common.exception.ServiceException;
import com.mobileleader.webform.common.exception.ServiceExceptionHandler;
import com.mobileleader.webform.common.model.CommonResponse;
import com.mobileleader.webform.common.utils.MediaTypes;
import com.mobileleader.webform.common.utils.Util;
import com.mobileleader.webform.sform.annotation.service.AnnotationBiz;
import com.mobileleader.webform.sform.data.Annotation;
import com.mobileleader.webform.sform.data.PageRequestOption;
import com.mobileleader.webform.sform.data.StreamingOption;
import com.mobileleader.webform.sform.model.AnnotationRequest;
import com.mobileleader.webform.sform.model.AnnotationResponse;
import com.mobileleader.webform.sform.model.ImageProcessRequest;
import com.mobileleader.webform.sform.model.SformFileInfoResponse;
import com.mobileleader.webform.sform.model.SformStreamingRequest;
import com.mobileleader.webform.sform.model.SfromDocInfoResponse;
import com.mobileleader.webform.sform.streaming.cache.PageStreamingData;
import com.mobileleader.webform.sform.streaming.service.DocBiz;
import com.mobileleader.webform.sform.streaming.service.ImageBiz;
import com.mobileleader.webform.sform.streaming.service.ImageProcessBiz;

@Controller
@RequestMapping(value="/sform")
public class EdocSformController extends ServiceExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(EdocSformController.class);
	
	@Autowired
	private ImageBiz imageBiz;

	@Autowired
	private ImageProcessBiz imageProcessBiz;

	@Autowired
	private AnnotationBiz annotationBiz;

	@Autowired
	private DocBiz docBiz;

	@RequestMapping(value="/file/{fileId:.+}.wfm", method=RequestMethod.GET)
	public void getFileByPath(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value="fileId") String fileId) {

		_getFile(request, response, fileId);
	}

	@RequestMapping(value="/file/down.wfm", method=RequestMethod.POST)
	public void getFileByPost(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String fileId) {

		_getFile(request, response, fileId);
	}

	@RequestMapping(value="/file/down.wfm", method=RequestMethod.GET)
	public void getFileByGet(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String fileId) {

		_getFile(request, response, fileId);
	}

	private void _getFile(HttpServletRequest request,
			HttpServletResponse response, String fileId) {
		logger.debug("getFile : " + fileId);

		byte[] data = imageBiz.getImage(fileId);

		if(data == null) {
			throw new ServiceException(ServiceError.NOT_FOUND_FILE);
		}

		FileInfo imageInfo = imageBiz.getImageInfo(fileId);

		String fileName = imageInfo.getFileName();
		String fileExt = imageInfo.getFileExt();
		int fileSize = (int)imageInfo.getFileSize();

		setFileDownloadHeader(request, response, fileName, fileExt, fileSize);

		writeBinaryResponse(response, data);
	}

	@RequestMapping(value="/doc/{docId:.+}/info.wfm", method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SfromDocInfoResponse getDocInfo(@PathVariable(value="docId") String docId) {

		return _getDocInfo(docId);
	}

	@RequestMapping(value="/doc/info.wfm", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SfromDocInfoResponse getDocInfo(@RequestBody Map<String, String> reqBody) {

		String docId = reqBody.get("docId");

		return _getDocInfo(docId);
	}

	private SfromDocInfoResponse _getDocInfo(String docId) {
		logger.debug("getDocInfo : " + docId);

		DocInfo docInfo = docBiz.getDocInfo(docId);

		if(docInfo == null) {
			throw new ServiceException(ServiceError.NOT_FOUND_FILE);
		}

		SfromDocInfoResponse response = new SfromDocInfoResponse(docInfo);

		logger.debug("getFileInfo response : " + response.toString());

		return response;
	}

	@RequestMapping(value="/file/{fileId:.+}/info.wfm", method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SformFileInfoResponse getFileInfo(@PathVariable(value="fileId") String fileId) {

		return _getFileInfo(fileId);
	}

	@RequestMapping(value="/file/info.wfm", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SformFileInfoResponse getFileInfo(@RequestBody Map<String, String> reqBody) {

		String fileId = reqBody.get("fileId");

		return _getFileInfo(fileId);
	}

	private SformFileInfoResponse _getFileInfo(String fileId) {
		logger.debug("getFileInfo : " + fileId);

		FileInfo fileInfo = imageBiz.getImageInfo(fileId);

		if(fileInfo == null) {
			throw new ServiceException(ServiceError.NOT_FOUND_FILE);
		}

		SformFileInfoResponse response = new SformFileInfoResponse(fileInfo);

		logger.debug("getFileInfo response : " + response.toString());

		return response;
	}

	@RequestMapping(value="/file/{fileId:.+}/page/{pageNo}.wfm", method={RequestMethod.GET, RequestMethod.POST},
			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getPageStreamByPath(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value="fileId") String fileId, @PathVariable(value="pageNo") int pageNo,
			@ModelAttribute SformStreamingRequest req, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		_getPageStream(request, response, fileId, pageNo, req);
	}



	@RequestMapping(value="/file/streaming.wfm", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void getPageStream(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SformStreamingRequest streamingRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		String fileId = streamingRequest.getFileId();
		int pageNo = streamingRequest.getPageNo();

		_getPageStream(request, response, fileId, pageNo, streamingRequest);
	}

	@RequestMapping(value="/file/streaming.wfm", method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getPageStream(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="fileId") String fileId, @RequestParam(value="pageNo") int pageNo,
			@ModelAttribute SformStreamingRequest streamingRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		_getPageStream(request, response, fileId, pageNo, streamingRequest);
	}

	private void _getPageStream(HttpServletRequest request,
			HttpServletResponse response, String fileId, int pageNo,
			PageRequestOption req) {

		StreamingOption streamingOption = new StreamingOption(req.getFileFormat(), req.getCompressFormat(),
				req.getCompressValue(), req.getImageSize(), false);

		if(streamingOption.getFileFormat() == null)
			streamingOption.setFileFormat("jpg");
		
		PageStreamingData streamingData = imageBiz.getPageStream(fileId, pageNo, streamingOption);

		String fileName = fileId + "_P" + pageNo;
		String fileFullName = fileName + "." + streamingData.getFileFormat();

		logger.debug("getPageStream : " + fileName + "\t" + req.toString());

		byte[] data = streamingData.getOrgImg();


		setFileDownloadHeader(request, response, fileFullName, streamingData.getFileFormat(), data.length);
		writeBinaryResponse(response, data);
	}

	@RequestMapping(value="/file/**/page/{pageNo}/thumbnail.wfm", method={RequestMethod.GET, RequestMethod.POST},
			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getPageThumnail(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) String fileId, @PathVariable(value="pageNo") int pageNo,
			@RequestParam(required = false, defaultValue = "320,480") String imageSize) {

		logger.debug("getPageThumnail() fileId={}",fileId); 
		StreamingOption streamingOption = new StreamingOption("jpg", "jpeg", "2", imageSize, true);

		_getPageThumbnail(request, response, fileId, pageNo, streamingOption);
	}

	@RequestMapping(value="/file/thumbnail.wfm", method=RequestMethod.POST,
			produces=MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void getPageThumnail(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SformStreamingRequest streamingRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		String fileId = streamingRequest.getFileId();
		int pageNo = streamingRequest.getPageNo();
		StreamingOption streamingOption = new StreamingOption("jpg", "jpeg", "2", streamingRequest.getImageSize(), true);

		_getPageThumbnail(request, response, fileId, pageNo, streamingOption);
	}

	private void _getPageThumbnail(HttpServletRequest request,
			HttpServletResponse response, String fileId, int pageNo, StreamingOption streamingOption) {

		String fileName = fileId + "_P" + pageNo + "_thumb";
		String fileFullName = fileName + "." + streamingOption.getFileFormat();

		logger.debug("getPageThumnail : " + fileName + "\t" + streamingOption.getImageSize());
		
		PageStreamingData streamingData = imageBiz.getPageStream(fileId, pageNo, streamingOption);
		byte[] data = streamingData.getOrgImg();


		setFileDownloadHeader(request, response, fileFullName, streamingData.getFileFormat(), data.length);
		writeBinaryResponse(response, data);
	}

	@RequestMapping(value="/file/{fileId:.+}/page/{pageNo}/annotation.wfm", method=RequestMethod.GET)
	@ResponseBody
	public AnnotationResponse getAnnotation(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value="fileId") String fileId, @PathVariable(value="pageNo") int pageNo,
			@ModelAttribute AnnotationRequest annotationRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		return _getAnnotation(annotationRequest);
	}

	@RequestMapping(value="/file/annotation.wfm", method=RequestMethod.POST)
	@ResponseBody
	public AnnotationResponse getAnnotation(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AnnotationRequest annotationRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		return _getAnnotation(annotationRequest);
	}

	private AnnotationResponse _getAnnotation(AnnotationRequest annotationRequest) {
		
		String fileId = annotationRequest.getFileId();
		String annotationId = annotationRequest.getAnnotationId();
		int pageNo = annotationRequest.getPageNo();

		Annotation annotation = annotationBiz.getPageAnnotation(fileId, annotationId, pageNo);

		AnnotationResponse response = new AnnotationResponse(
				fileId, annotationId, pageNo, annotation.getAnnotations(pageNo));

		logger.debug("getAnnotation response : " + response.toString());

		return response;
	}

	@RequestMapping(value="/file/{fileId:.+}/page/{pageNo}/annotation/create.wfm", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AnnotationResponse addAnnotation(@PathVariable(value="fileId") String fileId, @PathVariable(value="pageNo") int pageNo,
			@ModelAttribute AnnotationRequest annotationRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		return _addAnnotation(annotationRequest);
	}

	@RequestMapping(value="/file/annotation/create.wfm", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AnnotationResponse addAnnotation(@RequestBody AnnotationRequest annotationRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		return _addAnnotation(annotationRequest);
	}

	@SuppressWarnings("deprecation")
    private AnnotationResponse _addAnnotation(AnnotationRequest annotationRequest) {

		String fileId = annotationRequest.getFileId();
		String annotationId = annotationRequest.getAnnotationId();
		int pageNo = annotationRequest.getPageNo();

		Annotation annotation = new Annotation();
		annotation.setAnnotations(pageNo, annotationRequest.getAnnotation());

		String resultFileId = annotationBiz.setAnnotation(fileId, annotationId, pageNo, annotation);

		AnnotationResponse response = new AnnotationResponse(resultFileId, annotationId, pageNo);

		logger.debug("addAnnotation response : " + response.toString());

		return response;
	}

	@RequestMapping(value="/file/{fileId:.+}/page/{pageNo}/annotation/update.wfm", method=RequestMethod.GET)
	@ResponseBody
	public AnnotationResponse updateAnnotation(@PathVariable(value="fileId") String fileId, @PathVariable(value="pageNo") int pageNo,
			@ModelAttribute AnnotationRequest annotationRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		return _updateAnnotation(annotationRequest);
	}

	@RequestMapping(value="/file/annotation/update.wfm", method=RequestMethod.POST)
	@ResponseBody
	public AnnotationResponse updateAnnotation(@RequestBody AnnotationRequest annotationRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		return _updateAnnotation(annotationRequest);
	}

	private AnnotationResponse _updateAnnotation(AnnotationRequest annotationRequest) {

		String fileId = annotationRequest.getFileId();
		String annotationId = annotationRequest.getAnnotationId();
		int pageNo = annotationRequest.getPageNo();

		Annotation annotation = new Annotation();
		annotation.setAnnotations(pageNo, annotationRequest.getAnnotation());

		int result = annotationBiz.updateAnnotation(fileId, annotationId, pageNo, annotation);
		
		logger.debug("updateAnnotation result : " + result);

		AnnotationResponse response = new AnnotationResponse(fileId, annotationId, pageNo);

		logger.debug("updateAnnotation response : " + response.toString());

		return response;
	}


	@RequestMapping(method=RequestMethod.GET, value="/cache/clear.wfm")
	@ResponseBody
	public CommonResponse clearCache() {

		logger.debug("GET /cache/clear");

		imageBiz.clearCache();

		CommonResponse response = new CommonResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());

		return response;

	}

	@RequestMapping(value="/file/{fileId:.+}/page/{pageNo}/processing.wfm", method=RequestMethod.POST)
	@ResponseBody
	public void processImage(@PathVariable(value="fileId") String fileId, @PathVariable(value="pageNo") int pageNo,
			@ModelAttribute ImageProcessRequest ipRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		ipRequest.setFileId(fileId);
		ipRequest.setPageNo(pageNo);

		_processImage(ipRequest);
	}

	@RequestMapping(value="/file/processing.wfm", method=RequestMethod.POST)
	@ResponseBody
	public void processImage(@RequestBody ImageProcessRequest ipRequest, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			String errMsg = Util.makeRequestBindingErrorMsg(bindingResult.getFieldErrors());
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, errMsg);
		}

		_processImage(ipRequest);
	}

	private void _processImage(ImageProcessRequest ipRequest) {
		imageProcessBiz.process(ipRequest);
	}

	private void writeBinaryResponse(HttpServletResponse response, byte[] data) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(data);
			os.flush();
		} catch (IOException e) {
			throw new ServiceException(e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				logger.error("IOException", e);
			}
		}
	}

	private void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response,
			String fileName, String fileExt, long fileSize) {

		String userAgent = request.getHeader("User-Agent");

		try {
			if (userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
				fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
			} else if (userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
				fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
			} else if (userAgent.indexOf("Trident") > -1) { //MS IE 11
				fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
			} else { // 모질라나 오페라
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			logger.warn("UrlEncode exception. : " + fileName);
		}

		response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");
		response.setContentLength((int)fileSize);
		MediaTypes mediaTypes = MediaTypes.fromExtension(fileExt);
		response.setContentType(mediaTypes.getMime());
	}
}
