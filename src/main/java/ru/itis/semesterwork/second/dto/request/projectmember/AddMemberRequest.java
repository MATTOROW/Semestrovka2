package ru.itis.semesterwork.second.dto.request.projectmember;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.itis.semesterwork.second.model.ProjectRole;
import ru.itis.semesterwork.second.validation.annotation.ValueOfEnum;

@Schema(description = "Запрос на добавление участника")
public record AddMemberRequest(
        @Schema(description = "Username участника", example = "user123", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Username аккаунта обязательно")
        String username,

        @Schema(description = "Роль участника (ADMIN, MEMBER)", example = "MEMBER", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Роль обязательна")
        @ValueOfEnum(enumClass = ProjectRole.class, exclude = {"OWNER"})
        String role
) {}