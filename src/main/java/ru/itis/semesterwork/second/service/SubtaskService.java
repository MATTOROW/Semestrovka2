package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.dto.request.subtask.CreateSubtaskRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskInfoRequest;
import ru.itis.semesterwork.second.dto.request.subtask.UpdateSubtaskOrderRequest;
import ru.itis.semesterwork.second.dto.response.subtask.SubtaskResponse;
import ru.itis.semesterwork.second.dto.response.task.TaskStatusResponse;
import ru.itis.semesterwork.second.exception.SubtaskNotFoundException;
import ru.itis.semesterwork.second.mapper.SubtaskMapper;
import ru.itis.semesterwork.second.model.SubtaskEntity;
import ru.itis.semesterwork.second.model.SubtaskGroupEntity;
import ru.itis.semesterwork.second.model.TaskStatus;
import ru.itis.semesterwork.second.repository.SubtaskRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final SubtaskMapper subtaskMapper;
    private final HierarchyValidationService hierarchyValidationService;
    private final StatusService statusService;

    public SubtaskResponse findByInnerId(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UUID groupId) {
        return subtaskMapper.toResponse(
                hierarchyValidationService.validateSubtaskHierarchy(projectId, categoryId, taskId, groupId, innerId)
        );
    }

    public List<SubtaskResponse> findAllByGroupInnerId(UUID projectId, UUID categoryId, UUID taskId, UUID groupId) {
        SubtaskGroupEntity subtaskGroupEntity = hierarchyValidationService.validateSubtaskGroupHierarchy(projectId, categoryId, taskId, groupId);
        return subtaskGroupEntity.getSubtasks().stream().map(subtaskMapper::toResponse).toList();
    }

    @Transactional
    public UUID create(UUID projectId, UUID categoryId, UUID taskId, UUID groupId, CreateSubtaskRequest request) {
        SubtaskEntity subtaskEntity = subtaskMapper.toEntity(request);
        SubtaskGroupEntity subtaskGroupEntity = hierarchyValidationService.validateSubtaskGroupHierarchy(projectId, categoryId, taskId, groupId);
        subtaskEntity.setSubtaskGroup(subtaskGroupEntity);
        subtaskEntity.setPosition(subtaskRepository.countBySubtaskGroupInnerId(groupId));
        return subtaskRepository.save(subtaskEntity).getInnerId();
    }

    @Transactional
    public void updateByInnerId(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UUID groupId, UpdateSubtaskInfoRequest request) {
        SubtaskEntity subtaskEntity = hierarchyValidationService.validateSubtaskHierarchy(projectId, categoryId, taskId, groupId, innerId);
        subtaskMapper.updateSubtaskEntity(request, subtaskEntity);
        subtaskRepository.save(subtaskEntity);
    }

    @Transactional
    public void deleteByInnerId(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UUID groupId) {
        SubtaskEntity subtask = hierarchyValidationService.validateSubtaskHierarchy(projectId, categoryId, taskId, groupId, innerId);
        Integer pos = subtask.getPosition();

        subtaskRepository.updatePositionsMinus(groupId, pos);
        subtaskRepository.delete(subtask);
    }

    // TODO аналогично проверять uuid
    @Transactional
    public void updateOrder(UUID projectId, UUID categoryId, UUID taskId, UUID groupId, UpdateSubtaskOrderRequest request) {
        SubtaskGroupEntity subtaskGroupEntity = hierarchyValidationService.validateSubtaskGroupHierarchy(projectId, categoryId, taskId, groupId);

        List<SubtaskEntity> subtaskEntities = subtaskGroupEntity.getSubtasks();

        Map<UUID, SubtaskEntity> subtaskMap = subtaskEntities.stream()
                .collect(Collectors.toMap(SubtaskEntity::getInnerId, Function.identity()));

        List<UUID> orderedSubtaskIds = request.orderedSubtaskIds();

        for (int i = 0; i < orderedSubtaskIds.size(); i++) {
            UUID subtaskId = orderedSubtaskIds.get(i);
            SubtaskEntity subtask = subtaskMap.get(subtaskId);
            if (subtask != null) {
                subtask.setPosition(i);
            }
        }

        subtaskRepository.saveAll(subtaskEntities);
    }

    @Transactional
    public TaskStatusResponse changeStatus(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UUID groupId, Boolean completed) {
        SubtaskEntity subtaskEntity = hierarchyValidationService.validateSubtaskHierarchy(projectId, categoryId, taskId, groupId, innerId);
        if (!completed.equals(subtaskEntity.getCompleted())) {
            subtaskEntity.setCompleted(completed);
            subtaskRepository.save(subtaskEntity);
            TaskStatus newStatus = statusService.updateGroupStatus(subtaskEntity.getSubtaskGroup().getId());
            return new TaskStatusResponse(newStatus);
        } else {
            return new TaskStatusResponse(subtaskEntity.getSubtaskGroup().getTask().getStatus());
        }
    }
}
