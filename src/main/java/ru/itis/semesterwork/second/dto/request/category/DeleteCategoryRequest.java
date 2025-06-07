package ru.itis.semesterwork.second.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteCategoryRequest(

        @NotNull
        @Schema(description = "UUID категории в которую перенесутся все таски")
        UUID newCategoryId
) {}
