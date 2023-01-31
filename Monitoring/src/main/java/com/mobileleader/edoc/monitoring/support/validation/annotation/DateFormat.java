package com.mobileleader.edoc.monitoring.support.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.mobileleader.edoc.monitoring.support.validation.DateFormatValidator;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateFormatValidator.class)
public @interface DateFormat {

    String message() default "Invalid Date Format";
    
    String pattern() default "yyyy-MM-dd";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
