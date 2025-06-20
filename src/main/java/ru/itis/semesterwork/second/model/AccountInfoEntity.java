package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_info")
@Getter@Setter
@NoArgsConstructor
public class AccountInfoEntity {

    @Id
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    public int hashCode() {
        return accountId == null ? super.hashCode() : accountId.hashCode();
    }
}
