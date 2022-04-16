package com.project.bankproject.domain.exception;

/**
 * class EntityNotFoundException for exception when Entity NOT FOUND in DataBase.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
