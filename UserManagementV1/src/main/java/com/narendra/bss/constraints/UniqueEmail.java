package com.narendra.bss.constraints;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { UniqueEmailValidator.class})
@Target({ ElementType.TYPE })
@Retention(RUNTIME)

public @interface UniqueEmail {

	String message() default "{com.narendra.bss.constraints.UniqueEmail}";
	
	String emailFiled();
	String processForField();
	String idField();
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}