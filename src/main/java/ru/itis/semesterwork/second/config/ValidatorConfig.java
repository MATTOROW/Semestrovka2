package ru.itis.semesterwork.second.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.valueextraction.ValueExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itis.semesterwork.second.validation.model.NullableFieldValueExtractor;
import java.util.List;
import java.util.Optional;

@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator(Optional<List<ValueExtractor<?>>> extractors) {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .addValueExtractor(new NullableFieldValueExtractor())
                .buildValidatorFactory();
        return factory.getValidator();
    }
}
