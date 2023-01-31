package com.mobileleader.image.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mobileleader.image.exception.ServiceError;
import com.mobileleader.image.exception.ServiceException;
import com.mobileleader.image.server.model.response.BaseResponse;

/**
 * 서버 공통 ExceptionHandler 컨트롤러 
 * 
 * @author yh.kim
 */
@Controller
public class ExceptionCaughtController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionCaughtController.class);
	
	/*
	 * ServiceException handler
	 */
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse handleServiceException(ServiceException e) {
		logger.error("ServiceException caught", e);
		
		return setResponse(e.getErrorCode(), e.getMessage());
	}
	
	/*
	 * DataAccessException handler (SQLException)
	 */
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse handleSqlException(DataAccessException e) {
		logger.error("DataAccessException caught", e);

		String message = (e.getMessage() == null)? ServiceError.SQL_ERROR.getDescription(): e.getMessage();
		
		return setResponse(ServiceError.SQL_ERROR.getErrorCode(), message);
	}
	
	/*
	 * MethodArgumentNotValidException handler (Validation Error)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse handleValidException(MethodArgumentNotValidException e){
		logger.error("MethodArgumentNotValidException caught", e);
		
		return setResponse(e.getBindingResult());
	}
	
	/*
	 * BindException handler (Validation Error)
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse handleBindException(BindException e) {
		logger.error("BinException caught", e);
		
		return setResponse(e.getBindingResult());
	}
	
	/*
	 * 처리하지 못한 Exception handler
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse handleException(Exception e) {
		logger.error("Exception caught", e);
		
		return setResponse(ServiceError.INTERNAL_SERVER_ERROR.getErrorCode(), ServiceError.INTERNAL_SERVER_ERROR.getDescription());
	}
	
	/*
	 * validation Error 발생시 response set
	 */
	private BaseResponse setResponse(BindingResult bindingResult) {

		StringBuilder message = new StringBuilder();
		message.append(ServiceError.INVALID_REQUEST_PARAMETER.getDescription());
		message.append("\n");
		message.append("{ ");
		
		for(FieldError error : bindingResult.getFieldErrors()) {
			message.append("\n\t[");
			message.append(String.format("\"%s\" : \"%s\", %s", error.getField(),error.getRejectedValue(), error.getDefaultMessage()));
			message.append("]");
		}
		message.append("\n}");
		
		logger.info("{}", message);
		return setResponse(ServiceError.INVALID_REQUEST_PARAMETER.getErrorCode(), message.toString());
	}
	
	/*
	 * response set
	 */
	private BaseResponse setResponse(int errorCode, String errorMessage) {
		BaseResponse response = new BaseResponse();
		
		response.setResultCode(errorCode);
		response.setResultMessage(errorMessage);
		
		return response;
	}
}
