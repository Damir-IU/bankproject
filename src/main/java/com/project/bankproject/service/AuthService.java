package com.project.bankproject.service;

import com.project.bankproject.domain.entity.User;

/**
 * interface AuthService for class {@link User}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface AuthService {
    /**
     * Method for get token for User with @login and @password.
     *
     * @param login    - login of User
     * @param password - password of User
     * @return token for User
     */
    String login(String login, String password);

    /**
     * Method for save created User in DataBase.
     *
     * @param user - User object for saving
     * @return created User
     */
    User register(User user);
}
