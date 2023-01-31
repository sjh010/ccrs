package com.mobileleader.edoc.monitoring.support.exceptionhandler;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobileleader.edoc.monitoring.exceptions.MonitoringServiceException;
import com.mobileleader.edoc.monitoring.exceptions.RequestValidationException;
import com.mobileleader.edoc.monitoring.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Component
@ResponseBody
@Slf4j
public class GlobalRestContorllerAdvice {

    /**
     * TODO return type 변경하고 싶음 (Reponse 객체 따로 하나 만들어서)
     * AS-IS가 실패시 map으로 내려주는 형태로 하고 있어서 일단은 맞춰놓음
     * @author asyu (2019-05-31)
     */
    @ExceptionHandler(MonitoringServiceException.class)
    public Map<String, Object> handlerMonitoringServiceException(MonitoringServiceException e) {
        logger.error("MonitoringServiceException Cause - {}", e.getDetailMessage());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "fail");
        //result.put("message", e.getMessage());
        return result;
    }
    
    @ExceptionHandler(RequestValidationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleRequestValidationException(RequestValidationException e) throws JsonProcessingException {
        logger.error(JsonUtils.ObjectToJsonString("[" + e.getMessage() + "]", e.getErrors()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "fail");
        //result.put("message", e.getMessage());
        return result;
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleException(ConstraintViolationException e) throws JsonProcessingException {
        // TODO 메시지 제대로 찍기
        logger.error(JsonUtils.ObjectToJsonString("[" + e.getMessage() + "]", e.getCause()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "fail");
        //result.put("message", e.getLocalizedMessage());
        return result;
    }
}
