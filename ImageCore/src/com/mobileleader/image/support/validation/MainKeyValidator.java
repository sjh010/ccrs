package com.mobileleader.image.support.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.image.annotation.ValidMainKey;


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
			
			if(!key.equals(getPropKey())) {	
				context.buildConstraintViolationWithTemplate("invalid server profile")
				.addConstraintViolation();
				return false;
			}
		}
		
		return true;
	}
	
	private String getPropKey() {
		String propKey = "";

		String prop = System.getenv("SPRING_PROFILES_ACTIVE");
		
		logger.debug("SPRING_PROFILES_ACTIVE : {}", prop);
		
		if(prop != null && prop.startsWith("prd")) {
			propKey = "1";
		} else {
			propKey = "9";
		}
		
		return propKey;
	}

}
