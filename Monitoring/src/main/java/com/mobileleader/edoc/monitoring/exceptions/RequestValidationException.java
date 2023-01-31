package com.mobileleader.edoc.monitoring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.Getter;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequestValidationException extends RuntimeException{

    private static final long serialVersionUID = -4200811764095507841L;
    
    @Getter
    private Errors errors;

    public RequestValidationException() {
        super();
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestValidationException(String message) {
        super(message);
    }
    
    public RequestValidationException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }
    
    public RequestValidationException(Errors errors) {
        this.errors = errors;
    }
    
    public RequestValidationException(String message, Throwable cause, Errors errors) {
        super(message, cause);
        this.errors = errors;
    }
}
