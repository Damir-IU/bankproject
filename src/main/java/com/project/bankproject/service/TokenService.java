package com.project.bankproject.service;

import com.project.bankproject.domain.entity.User;

/**
 * interface TokenService for class {@link User} and generating token.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
public interface TokenService {
    /**
     * Method for generating token.
     *
     * @param user - User for generate token
     * @return generated token as String
     */
    String generateToken(User user);

    /**
     * Method for checking validation of token
     * and getting login from token.
     *
     * @param token - String of token
     * @return login of User from token
     */
    String validateTokenAndGetLogin(String token);
}
