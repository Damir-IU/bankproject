package com.project.bankproject.service;

import com.project.bankproject.domain.entity.User;

import java.util.UUID;

/**
 * interface CheckOwnerService for methods with checking current {@link User}.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
public interface CheckOwnerService {

    /**
     * Method for checking owner of Profile.
     *
     * @return true if current User is owner of Profile.
     */
    boolean ownerUser(UUID userId);

    /**
     * Method for checking owner of Profile.
     *
     * @return true if current User is owner of Profile.
     */
    boolean ownerProfile(UUID profileId);

    /**
     * Method for checking owner of BankAccount.
     *
     * @return true if current User is owner of BankAccount.
     */
    boolean ownerBankAccount(UUID bankAccountId);
}
