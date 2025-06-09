package ru.itis.semesterwork.second.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.semesterwork.second.model.TaskStatus;
import ru.itis.semesterwork.second.repository.SubtaskGroupRepository;
import ru.itis.semesterwork.second.repository.SubtaskRepository;
import ru.itis.semesterwork.second.repository.TaskRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final SubtaskRepository subtaskRepository;
    private final SubtaskGroupRepository subtaskGroupRepository;
    private final TaskRepository taskRepository;

    public TaskStatus updateGroupStatus(Long groupId) {
        boolean allCompleted = subtaskRepository.areAllCompletedByGroupId(groupId);
        subtaskGroupRepository.updateCompletedById(groupId, allCompleted);
        return updateTaskStatus(subtaskGroupRepository.getTaskIdById(groupId));
    }

    public TaskStatus updateTaskStatus(Long taskId) {
        boolean allGroupsCompleted = subtaskGroupRepository.areAllGroupsCompletedByTaskId(taskId);
        Instant deadline = taskRepository.getDeadlineById(taskId);
        TaskStatus newStatus = null;
        if (allGroupsCompleted) {
            if (deadline != null && Instant.now().isAfter(deadline)) {
                newStatus = TaskStatus.COMPLETED_LATE;
            } else {
                newStatus = TaskStatus.COMPLETED;
            }
        } else {
            if (deadline != null && Instant.now().isAfter(deadline)) {
                newStatus = TaskStatus.EXPIRED;
            } else {
                newStatus = TaskStatus.NOT_COMPLETED;
            }
        }
        taskRepository.updateStatusById(taskId, newStatus);
        return newStatus;
    }
}
