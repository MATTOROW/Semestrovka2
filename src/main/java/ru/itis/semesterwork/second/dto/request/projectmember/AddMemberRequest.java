package ru.itis.semesterwork.second.dto.request.projectmember;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.itis.semesterwork.second.model.ProjectRole;
import ru.itis.semesterwork.second.validation.annotation.ValueOfEnum;

public record AddMemberRequest(

        @NotNull(message = "ID аккаунта обязательно")
        @Schema(description = "Username аккаунта", example = "NikOS")
        String username,

        @NotBlank(message = "Роль обязательна")
        @ValueOfEnum(enumClass = ProjectRole.class, exclude = {"OWNER"})
        @Schema(description = "Роль (ADMIN, MEMBER, ...)", example = "MEMBER")
        String role
) {}