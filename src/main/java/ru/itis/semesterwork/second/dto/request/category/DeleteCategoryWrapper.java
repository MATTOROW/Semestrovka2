package ru.itis.semesterwork.second.dto.request.category;

import jakarta.validation.constraints.NotNull;
import ru.itis.semesterwork.second.validation.annotation.CategoryChangeValid;

import java.util.UUID;

@CategoryChangeValid
public record DeleteCategoryWrapper (
        UUID categoryId,

        @NotNull
        UUID newCategoryId
) {}
