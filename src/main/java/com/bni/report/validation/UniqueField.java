package com.bni.report.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueFieldValidator.class)
public @interface UniqueField {
    String message() default "Field must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
