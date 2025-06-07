package ru.itis.semesterwork.second.dto.request.task;

import jakarta.validation.constraints.Size;
import ru.itis.semesterwork.second.validation.model.NullableField;

import java.time.LocalDateTime;

public record UpdateTaskInfoRequest(


        NullableField<
                @Size(max = 255, message = "Task name must be at most 255 characters")
                String
                > name,


        NullableField<
                @Size(max = 1000, message = "Description must be at most 1000 characters")
                String
                > description,

        NullableField<LocalDateTime> deadline
) {}
