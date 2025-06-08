package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "subtask")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SubtaskEntity {

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
    @JoinColumn(name = "subtask_group_id", nullable = false)
    private SubtaskGroupEntity subtaskGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtaskEntity that)) return false;
        return innerId.equals(that.innerId);
    }

    @Override
    public int hashCode() {
        return innerId.hashCode();
    }
}
