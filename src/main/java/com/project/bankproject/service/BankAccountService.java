package com.project.bankproject.service;

import com.project.bankproject.domain.entity.BankAccount;

import java.util.List;
import java.util.UUID;

/**
 * interface BankAccountService for class {@link BankAccount}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface BankAccountService {
    /**
     * Method for finding BankAccount from DataBase.
     *
     * @param id - id of BankAccount object
     * @return BankAccount from DataBase
     */
    BankAccount findById(UUID id);

    /**
     * Method for save created BankAccount in DataBase.
     *
     * @return created BankAccount with balance = 0.0
     */
    BankAccount create();

    /**
     * Method for finding all of BankAccounts.
     *
     * @return list of BankAccount
     */
    List<BankAccount> findAllByUserId();

    /**
     * Method for changing balance of BankAccount.
     *
     * @param id     - id of BankAccount
     * @param number - number for adding to balance
     * @return updated BankAccount
     */
    BankAccount changeBalance(UUID id, double number);

    /**
     * Method for deleting BankAccount
     *
     * @param id - id of BankAccount
     */
    void delete(UUID id);

    /**
     * Method for updating BankAccount in DataBase.
     *
     * @return updated BankAccount
     */
    BankAccount update(BankAccount bankAccount);
}
