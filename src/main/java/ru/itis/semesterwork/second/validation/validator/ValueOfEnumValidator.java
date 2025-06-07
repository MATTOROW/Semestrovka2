package ru.itis.semesterwork.second.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itis.semesterwork.second.validation.annotation.ValueOfEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .filter(name -> Arrays.stream(annotation.exclude()).noneMatch(ex -> ex.equalsIgnoreCase(name)))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        boolean isValid = acceptedValues.contains(value.toString());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    context.getDefaultConstraintMessageTemplate()
                            .replace("{acceptedValues}", String.join(", ", acceptedValues))
            ).addConstraintViolation();
        }

        return isValid;
    }
}
