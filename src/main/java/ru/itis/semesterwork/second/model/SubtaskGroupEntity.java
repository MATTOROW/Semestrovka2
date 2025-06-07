package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
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
    private Boolean completed;

    @Column(nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;

    @OneToMany(mappedBy = "subtaskGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<SubtaskEntity> subtasks = new HashSet<>();

    public void addSubtask(SubtaskEntity subtask) {
        subtasks.add(subtask);
        subtask.setSubtaskGroup(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtaskGroupEntity that)) return false;
        return innerId.equals(that.innerId);
    }

    @Override
    public int hashCode() {
        return innerId.hashCode();
    }
}
