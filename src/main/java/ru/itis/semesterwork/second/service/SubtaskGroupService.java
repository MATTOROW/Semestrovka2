package ru.itis.semesterwork.second.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.CreateSubtaskGroupRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupInfoRequest;
import ru.itis.semesterwork.second.dto.request.subtaskgroup.UpdateSubtaskGroupOrderRequest;
import ru.itis.semesterwork.second.dto.response.subtaskgroup.SubtaskGroupResponse;
import ru.itis.semesterwork.second.exception.SubtaskGroupNotFoundException;
import ru.itis.semesterwork.second.mapper.SubtaskGroupMapper;
import ru.itis.semesterwork.second.model.SubtaskEntity;
import ru.itis.semesterwork.second.model.SubtaskGroupEntity;
import ru.itis.semesterwork.second.model.TaskEntity;
import ru.itis.semesterwork.second.repository.SubtaskGroupRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubtaskGroupService {

    private final SubtaskGroupMapper subtaskGroupMapper;
    private final SubtaskGroupRepository subtaskGroupRepository;
    private final HierarchyValidationService hierarchyValidationService;

    public SubtaskGroupResponse findByInnerId(UUID innerId, UUID projectId, UUID categoryId, UUID taskId) {
        return subtaskGroupMapper.toResponse(
                hierarchyValidationService.validateSubtaskGroupHierarchy(projectId, categoryId, taskId, innerId)
        );
    }

    public List<SubtaskGroupResponse> findByTaskInnerId(UUID projectId, UUID categoryId, UUID taskId) {
        TaskEntity taskEntity = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, taskId);

        return taskEntity.getSubtaskGroups().stream().map(subtaskGroupMapper::toResponse).toList();
    }

    @Transactional
    public UUID create(UUID projectId, UUID categoryId, UUID taskId, CreateSubtaskGroupRequest request) {
        SubtaskGroupEntity subtaskGroupEntity = subtaskGroupMapper.toEntity(request);
        TaskEntity taskEntity = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, taskId);
        subtaskGroupEntity.setTask(taskEntity);
        subtaskGroupEntity.setPosition(subtaskGroupRepository.countByTaskInnerId(taskId));
        return subtaskGroupRepository.save(subtaskGroupEntity).getInnerId();
    }

    @Transactional
    public void updateByInnerId(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, UpdateSubtaskGroupInfoRequest request) {
        SubtaskGroupEntity subtaskGroupEntity = hierarchyValidationService.validateSubtaskGroupHierarchy(projectId, categoryId, taskId, innerId);
        subtaskGroupMapper.updateSubtaskGroupEntity(request, subtaskGroupEntity);
        subtaskGroupRepository.save(subtaskGroupEntity);
    }

    @Transactional
    public void deleteByInnerId(UUID innerId, UUID projectId, UUID categoryId, UUID taskId) {
        SubtaskGroupEntity subtaskGroup = hierarchyValidationService.validateSubtaskGroupHierarchy(projectId, categoryId, taskId, innerId);

        Integer pos = subtaskGroup.getPosition();

        subtaskGroupRepository.updatePositionsMinus(taskId, pos);
        subtaskGroupRepository.delete(subtaskGroup);
    }

    // TODO добавить проверку что все uuid переданы верно.
    @Transactional
    public void updateOrder(UUID projectId, UUID categoryId, UUID taskId, UpdateSubtaskGroupOrderRequest request) {
        TaskEntity taskEntity = hierarchyValidationService.validateTaskHierarchy(projectId, categoryId, taskId);
        List<SubtaskGroupEntity> subtaskGroupEntities = taskEntity.getSubtaskGroups();
        Map<UUID, SubtaskGroupEntity> groupMap = subtaskGroupEntities.stream()
                .collect(Collectors.toMap(SubtaskGroupEntity::getInnerId, Function.identity()));

        List<UUID> orderedGroupIds = request.orderedGroupIds();

        for (int i = 0; i < orderedGroupIds.size(); i++) {
            UUID groupId = orderedGroupIds.get(i);
            SubtaskGroupEntity group = groupMap.get(groupId);
            if (group != null) {
                group.setPosition(i);
            }
        }

        subtaskGroupRepository.saveAll(subtaskGroupEntities);
    }

    @Transactional
    public void changeStatus(UUID innerId, UUID projectId, UUID categoryId, UUID taskId, Boolean completed) {
        SubtaskGroupEntity subtaskGroupEntity = hierarchyValidationService.validateSubtaskGroupHierarchy(projectId, categoryId, taskId, innerId);
        if (!completed.equals(subtaskGroupEntity.getCompleted())) {
            subtaskGroupEntity.setCompleted(completed);
            List<SubtaskEntity> subtaskEntities = subtaskGroupEntity.getSubtasks();
            subtaskEntities.forEach(s -> s.setCompleted(completed));
            subtaskGroupRepository.save(subtaskGroupEntity);
        }
    }
}
