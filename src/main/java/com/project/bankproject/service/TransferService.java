package com.project.bankproject.service;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.Profile;

import java.util.UUID;

/**
 * interface TransferService for classes {@link Profile} and {@link BankAccount}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface TransferService {
    /**
     * Method for transfer from Profile to another Profile.
     *
     * @param toProfileId - id of target Profile
     * @return updated source Profile
     */
    Profile transferFromProfileToProfile(UUID toProfileId, double number);

    /**
     * Method for transfer from Profile to BankAccount.
     *
     * @param toBankAccountId - id of target BankAccount
     * @return updated source Profile
     */
    Profile transferFromProfileToBankAccount(UUID toBankAccountId, double number);

    /**
     * Method for transfer from BankAccount to Profile.
     *
     * @param fromBankAccountId - id of source BankAccount
     * @param toProfileId       - id of target Profile
     * @return updated source BankAccount
     */
    BankAccount transferFromBankAccountToProfile(UUID fromBankAccountId, UUID toProfileId, double number);

    /**
     * Method for transfer from BankAccount to another BankAccount.
     *
     * @param fromBankAccountId - id of source BankAccount
     * @param toBankAccountId   - id of target BankAccount
     * @return updated source BankAccount
     */
    BankAccount transferFromBankAccountToBankAccount(UUID fromBankAccountId, UUID toBankAccountId, double number);
}
