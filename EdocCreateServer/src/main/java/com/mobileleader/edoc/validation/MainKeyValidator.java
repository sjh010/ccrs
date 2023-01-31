package com.mobileleader.edoc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.edoc.annotation.ValidMainKey;
import com.mobileleader.edoc.properties.EdocServiceProp;

public class MainKeyValidator implements ConstraintValidator<ValidMainKey, String>{
	
	private static Logger logger = LoggerFactory.getLogger(MainKeyValidator.class);

	@Override
	public void initialize(ValidMainKey constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value != null && value.length() == 14) {
			
			context.disableDefaultConstraintViolation();
			
			String key = String.valueOf(value.charAt(8));
			
			logger.info("profile : {}", EdocServiceProp.getProfile());
			
			if(!key.equals(EdocServiceProp.getProfileKey())) {	
				context.buildConstraintViolationWithTemplate("invalid server profile")
				.addConstraintViolation();
				return false;
			}
		}
		
		return true;
	}

}
