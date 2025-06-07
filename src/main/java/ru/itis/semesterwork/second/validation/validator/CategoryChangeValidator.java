package ru.itis.semesterwork.second.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.itis.semesterwork.second.dto.request.category.DeleteCategoryWrapper;
import ru.itis.semesterwork.second.validation.annotation.CategoryChangeValid;

public class CategoryChangeValidator implements ConstraintValidator<CategoryChangeValid, DeleteCategoryWrapper> {

    @Override
    public boolean isValid(DeleteCategoryWrapper wrapper, ConstraintValidatorContext context) {
        if (wrapper.categoryId() == null || wrapper.newCategoryId() == null) {
            return true;
        }

        return !wrapper.categoryId().equals(wrapper.newCategoryId());
    }
}

