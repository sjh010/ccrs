package com.mobileleader.edoc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mobileleader.edoc.model.response.BaseResponse;

/**
 * Exception Handler Class
 * 
 * @author yh.kim
 */
@ControllerAdvice
public class EdocServerExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(EdocServerExceptionHandler.class);
	
	/*
	 * EdocServerException Handler 
	 */
	@ExceptionHandler(EdocServerException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse except(EdocServerException e){
		logger.error("EdocServerException Handler", e);

		return setResponse(e.getStatusCode(), e.getMessage(), e.getErrorCode());
	}
	
	/*
	 * DataAccessException (DB 관련 Exception) handler
	 */
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse except(DataAccessException e){
		logger.error("DataAccessException Handler", e);
		
		EdocServerStatus errorType = EdocServerStatus.SQL_ERROR;
		
		return setResponse(errorType.getStatusCode(), e.getMessage(), errorType.getResultCode());
	}

	/*
	 * 처리되지 않은 Exception Handler
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse except(Exception e){
		logger.error("Exception Handler", e);

		EdocServerStatus errorType = EdocServerStatus.INTERNAL_SERVER_ERROR;

		return setResponse(errorType.getStatusCode(), errorType.getResultMessage() + " : " + e.getMessage(), errorType.getResultCode());
	}
	
	/*
	 * MethodArgumentNotValidException Handler (Validation Error)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse handleValidateException(MethodArgumentNotValidException e) {
		logger.error("MethodArgumentNotValidException Handler", e);
		
		return bindException(new BindException(e.getBindingResult()));
	}
	
	/*
	 * BindException Handler (Validation Error)
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BaseResponse bindException(BindException e){
		logger.error("BindingException Handler", e);

		StringBuilder message = new StringBuilder();
		EdocServerStatus errorType = EdocServerStatus.INVALID_REQUEST;
		
		message.append(errorType.getResultMessage());
		message.append("\n");
		message.append("{ ");

		for(FieldError error : e.getBindingResult().getFieldErrors()) {
			message.append("\n\t[");
			message.append(error.getDefaultMessage());
			message.append("]");
		}
		message.append("\n}");
		
		return setResponse(errorType.getStatusCode(), new String(message), errorType.getResultCode());
	}
	
	private BaseResponse setResponse(int resultCode, String resultMessage, String corCode){
		
		BaseResponse response = new BaseResponse();
		response.setStatusCode(resultCode);
		response.setResultMessage(resultMessage);
		response.setResultCode(corCode);
		
		logger.debug("ErrorResponse = {}", response.toString());
		
		return response;
	}

}
