package com.mobileleader.edoc.monitoring.support.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.mobileleader.edoc.monitoring.support.validation.annotation.DateFormat;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String>{

    private String pattern;
    
    @Override
    public void initialize(DateFormat constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setLenient(false);
            format.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
