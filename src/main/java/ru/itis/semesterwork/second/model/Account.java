package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "account")
@Getter@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 254, unique = true, nullable = false)
    private String email;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(name = "inner_id", unique = true)
    private UUID innerId;

    private boolean enabled;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountInfo accountInfo;

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        accountInfo.setAccount(this);
    }
}
