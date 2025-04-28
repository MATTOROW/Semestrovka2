package ru.itis.semesterwork.second.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.semesterwork.second.dto.request.ProjectRequest;
import ru.itis.semesterwork.second.dto.response.ProjectResponse;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/projects")
public interface ProjectRestAPI {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ProjectResponse> getAllProjects();

    @GetMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    ProjectResponse findByInnerId(@PathVariable("innerId") UUID innerId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID create(@RequestBody ProjectRequest projectRequest);

    @PutMapping("/{inner_id}")
    @ResponseStatus(HttpStatus.OK)
    void updateByInnerId(@PathVariable("inner_id") UUID innerId, @RequestBody ProjectRequest projectRequest);

    @DeleteMapping("/{innerId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteByInnerId(@PathVariable("innerId") UUID innerId);
}
