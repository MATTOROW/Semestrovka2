package ru.itis.semesterwork.second.dto.response;

import java.util.List;

public record PageResponse<T>(
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        List<T> content
) {}
