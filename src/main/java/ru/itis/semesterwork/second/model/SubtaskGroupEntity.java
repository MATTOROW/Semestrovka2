package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "subtask_group")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SubtaskGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false, unique = true, updatable = false)
    private UUID innerId = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private Boolean completed = false;

    @Column
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;

    @OneToMany(mappedBy = "subtaskGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @OrderBy("position ASC")
    private List<SubtaskEntity> subtasks = new ArrayList<>();

    public void addSubtask(SubtaskEntity subtask) {
        subtasks.add(subtask);
        subtask.setSubtaskGroup(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtaskGroupEntity that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return innerId.hashCode();
    }
}
