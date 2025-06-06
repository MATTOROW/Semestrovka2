package ru.itis.semesterwork.second.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.semesterwork.second.validation.validator.CategoryChangeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CategoryChangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryChangeValid {
    String message() default "New category must not match the one being deleted";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
