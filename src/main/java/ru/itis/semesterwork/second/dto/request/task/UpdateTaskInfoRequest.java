package ru.itis.semesterwork.second.dto.request.task;

import jakarta.validation.constraints.Size;
import ru.itis.semesterwork.second.validation.model.NullableField;

import java.time.Instant;

public record UpdateTaskInfoRequest(


        NullableField<
                @Size(max = 70, message = "Task name must be at most 70 characters")
                String
                > name,


        NullableField<
                @Size(max = 1000, message = "Description must be at most 1000 characters")
                String
                > description,

        NullableField<Instant> deadline
) {}
