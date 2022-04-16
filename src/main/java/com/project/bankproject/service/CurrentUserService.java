package com.project.bankproject.service;

import com.project.bankproject.domain.entity.User;

/**
 * interface CurrentUserService for getting current {@link User}.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
public interface CurrentUserService {
    /**
     * Method for getting User from Authentication.
     *
     * @return current User.
     */
    User currentUser();
}
