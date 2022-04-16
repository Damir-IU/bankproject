package com.project.bankproject.repository;

import com.project.bankproject.domain.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * interface BankAccountRepository extends {@link JpaRepository} for class {@link BankAccount}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    /**
     * Method for finding all by User id.
     *
     * @param userId - id of User
     * @return list of BankAccounts
     */
    List<BankAccount> findAllByUserId(UUID userId);
}
