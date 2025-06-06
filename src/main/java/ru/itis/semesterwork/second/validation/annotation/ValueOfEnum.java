package ru.itis.semesterwork.second.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.itis.semesterwork.second.validation.validator.ValueOfEnumValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "must be any of: {acceptedValues}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
