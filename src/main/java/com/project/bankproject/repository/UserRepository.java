package com.project.bankproject.repository;

import com.project.bankproject.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * interface UserRepository extends {@link JpaRepository} for class {@link User}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Method for finding Optional of User with login from DataBase.
     *
     * @param login - login of User object
     * @return Optional of User with @login
     */
    Optional<User> findByLogin(String login);
}
