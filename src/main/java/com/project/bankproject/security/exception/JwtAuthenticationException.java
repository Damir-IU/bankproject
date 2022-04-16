package com.project.bankproject.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * class JwtAuthenticationException for Authetication exception.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
