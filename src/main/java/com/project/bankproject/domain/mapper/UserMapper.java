package com.project.bankproject.domain.mapper;

import com.project.bankproject.domain.dto.RegisterUserDto;
import com.project.bankproject.domain.dto.UserDto;
import com.project.bankproject.domain.dto.UserUpdateDto;
import com.project.bankproject.domain.entity.User;

/**
 * interface UserMapper for mapping Dto layer and for class {@link User}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface UserMapper {
    /**
     * Method for mapping User to UserDto.
     *
     * @param source - User object for mapping
     * @return UserDto object
     */
    UserDto toDto(User source);

    /**
     * Method for mapping RegisterUserDto to User.
     *
     * @param source - RegisterUserDto object for mapping
     * @return User object
     */
    User fromCreateDto(RegisterUserDto source);

    /**
     * Method for mapping UserUpdateDto to User.
     *
     * @param source - UserUpdateDto object for mapping
     * @return User object
     */
    User fromUpdateDto(UserUpdateDto source);

    /**
     * Method for merge-update from source User object to target User object.
     *
     * @param source - User object source for update
     * @param target - User object target for update
     * @return merge-updated User object
     */
    User merge(User source, User target);
}
