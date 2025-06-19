package ru.itis.semesterwork.second.dto.request.projectmember;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import ru.itis.semesterwork.second.model.ProjectRole;
import ru.itis.semesterwork.second.validation.annotation.ValueOfEnum;

@Schema(description = "Запрос на обновление роли участника")
public record UpdateMemberRoleRequest(
        @Schema(description = "Новая роль участника", example = "ADMIN", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Роль обязательна")
        @ValueOfEnum(enumClass = ProjectRole.class)
        String role
) {}