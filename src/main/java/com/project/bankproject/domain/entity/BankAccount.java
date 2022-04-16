package com.project.bankproject.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;

/**
 * class BankAccount for connection with the table bank_accounts in DataBase.
 * Domain BankAccount object.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "bank_accounts")
public class BankAccount extends BaseEntity {
    /**
     * balance of BankAccount
     */
    private double balance;

    /**
     * user of BankAccount
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
