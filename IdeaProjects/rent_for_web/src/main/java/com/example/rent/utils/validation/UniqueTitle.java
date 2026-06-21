package com.example.rent.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueTitleValidator.class)
public @interface UniqueTitle {
    String message() default "Название уже используется!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
