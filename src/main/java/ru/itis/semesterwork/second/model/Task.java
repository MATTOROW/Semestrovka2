package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime startDate;

    @Column(nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime endDate;

    @Column(name = "inner_id", unique = true)
    private UUID innerId;

    @Column(length = 20, nullable = false)
    private String status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subtask> subtasks;

    public void addSubtask(Subtask subtask) {
        if (subtasks != null) {
            subtask.setTask(this);
            subtasks.add(subtask);
        }
    }

    public void removeSubtask(Subtask subtask) {
        if (subtasks != null) {
            subtasks.remove(subtask);
            subtask.setTask(null);
        }
    }
}
