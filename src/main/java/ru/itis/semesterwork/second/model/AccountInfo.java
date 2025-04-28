package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_info")
@Getter@Setter
@NoArgsConstructor
public class AccountInfo {

    @Id
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

}
