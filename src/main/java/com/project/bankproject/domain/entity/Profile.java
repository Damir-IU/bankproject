package com.project.bankproject.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static javax.persistence.FetchType.LAZY;

/**
 * class Profile for connection with the table profiles in DataBase.
 * Domain Profile object.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "profiles")
public class Profile extends BaseEntity {
    /**
     * first name of User
     */
    private String firstName;

    /**
     * last name of User
     */
    private String lastName;

    /**
     * balance of profile
     */
    private double balance;

    /**
     * user of Profile
     */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
