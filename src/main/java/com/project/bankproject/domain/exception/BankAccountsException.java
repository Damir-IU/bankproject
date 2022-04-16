package com.project.bankproject.domain.exception;

/**
 * class BankAccountsException for exception with Bank Accounts.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public class BankAccountsException extends RuntimeException {
    public BankAccountsException(String message) {
        super(message);
    }
}
