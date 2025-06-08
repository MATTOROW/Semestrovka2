package ru.itis.semesterwork.second.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.semesterwork.second.dto.response.project.ProjectResponse;
import ru.itis.semesterwork.second.service.ProjectService;

import java.util.UUID;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public String accountProjects(Model model) {
        return "all-projects";
    }

    @GetMapping("/{projectId}")
    public String project(@PathVariable UUID projectId, Model model) {
        ProjectResponse response = projectService.getByInnerId(projectId);
        model.addAttribute("project", response);
        return "project";
    }
}
