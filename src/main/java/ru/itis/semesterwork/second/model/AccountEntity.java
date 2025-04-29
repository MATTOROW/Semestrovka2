package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter@Setter
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 254, unique = true, nullable = false)
    private String email;

    @Column(length = 60, nullable = false)
    private String password;

    private boolean enabled;

    @OneToOne(mappedBy = "accountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountInfoEntity accountInfoEntity;

    public void setAccountInfoEntity(AccountInfoEntity accountInfoEntity) {
        this.accountInfoEntity = accountInfoEntity;
        accountInfoEntity.setAccountEntity(this);
    }
}
