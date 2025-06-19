package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "task")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false, unique = true, updatable = false)
    private UUID innerId = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TaskStatus status = TaskStatus.NOT_COMPLETED;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false, updatable = false, columnDefinition = "timestamp")
    private Instant createDate;

    @Column(name = "end_date", columnDefinition = "timestamp")
    private Instant endDate;

    @Column(columnDefinition = "timestamp")
    private Instant deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private AccountEntity author;

    @Column
    private Integer position;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @OrderBy("position ASC")
    private List<SubtaskGroupEntity> subtaskGroups = new ArrayList<>();

    public void addSubtaskGroup(SubtaskGroupEntity group) {
        subtaskGroups.add(group);
        group.setTask(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskEntity that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

