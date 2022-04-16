package com.project.bankproject.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

/**
 * class User for connection with the table users in DataBase.
 * Domain User object.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
@Accessors(chain = true)
public class User extends BaseEntity {
    /**
     * login of User
     */
    private String login;

    /**
     * email of User
     */
    private String email;

    /**
     * password of User
     */
    private String password;

    /**
     * role of User
     */
    @Enumerated(STRING)
    private Role role;

    /**
     * profile of User
     */
    @OneToOne(mappedBy = "user", fetch = LAZY)
    private Profile profile;

    /**
     * list of Bank Accounts of User
     */
    @Setter(PRIVATE)
    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<BankAccount> bankAccounts = new ArrayList<>();

    public void addBankAccounts(BankAccount account) {
        this.bankAccounts.add(account);
        account.setUser(this);
    }

    public void removeBankAccount(BankAccount account) {
        this.bankAccounts.remove(account);
    }
}
