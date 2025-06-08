package ru.itis.semesterwork.second.dto.response;

import java.util.List;

public record CustomPageResponseDto<T>(
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last,
        List<T> content
) {}
