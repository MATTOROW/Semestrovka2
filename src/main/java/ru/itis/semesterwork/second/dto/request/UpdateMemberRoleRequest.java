package ru.itis.semesterwork.second.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import ru.itis.semesterwork.second.model.ProjectRole;
import ru.itis.semesterwork.second.validation.annotation.ValueOfEnum;

public record UpdateMemberRoleRequest(

        @NotBlank(message = "Роль обязательна")
        @ValueOfEnum(enumClass = ProjectRole.class)
        @Schema(description = "Новая роль", example = "ADMIN")
        String role
) {}