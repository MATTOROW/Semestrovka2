package ru.itis.semesterwork.second.dto.response.category;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CategoryResponse(

        @Schema(description = "UUID категории")
        UUID id,

        @Schema(description = "Название категории", example = "В работе")
        String name,

        @Schema(description = "Цвет в HEX", example = "#FF0000")
        String color
) {}
