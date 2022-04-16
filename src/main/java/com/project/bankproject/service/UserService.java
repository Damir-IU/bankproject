package com.project.bankproject.service;

import com.project.bankproject.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * interface UserService for class {@link User}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface UserService {
    /**
     * Method for finding User with id from DataBase.
     *
     * @param id - @id of User object
     * @return User from DataBase
     */
    User findById(UUID id);

    /**
     * Method for finding User with @login from DataBase.
     *
     * @param login - @login of User object
     * @return User from DataBase
     */
    User findByLogin(String login);

    /**
     * Method for finding User from DataBase and update it.
     *
     * @param id     - @id of User object
     * @param source - User object for update
     * @return updated User from DataBase
     */
    User update(UUID id, User source);

    /**
     * Method for finding Users from DataBase with pagination information.
     *
     * @param pageable - param for pagination information
     * @return Page of Users from DataBase
     */
    Page<User> findAll(Pageable pageable);
}
