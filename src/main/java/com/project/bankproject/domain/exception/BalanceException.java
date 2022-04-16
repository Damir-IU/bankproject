package com.project.bankproject.domain.exception;

/**
 * class BalanceException for exception with Balance of Profile or Bank Accounts.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public class BalanceException extends RuntimeException {
    public BalanceException(String message) {
        super(message);
    }
}
