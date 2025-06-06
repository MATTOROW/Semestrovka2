package ru.itis.semesterwork.second.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 254, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 60, nullable = false)
    private String hashed_password;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountInfoEntity accountInfoEntity;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProjectMemberEntity> projects = new HashSet<>();

    public void setAccountInfoEntity(AccountInfoEntity accountInfoEntity) {
        this.accountInfoEntity = accountInfoEntity;
        accountInfoEntity.setAccount(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;


        if (id != null && that.id != null) {
            return id.equals(that.id) && username.equals(that.username) && email.equals(that.email);
        }
        return username.equals(that.username) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
