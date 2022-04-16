package com.project.bankproject.domain.mapper.Impl;

import com.project.bankproject.domain.dto.RegisterUserDto;
import com.project.bankproject.domain.dto.UserDto;
import com.project.bankproject.domain.dto.UserUpdateDto;
import com.project.bankproject.domain.entity.BaseEntity;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * class UserMapperImpl implementation of {@link UserMapper} interface.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User source) {
        Set<UUID> bankAccountIds = source
                .getBankAccounts()
                .stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
        UUID profileId;
        if (source.getProfile() == null) {
            profileId = null;
        } else {
            profileId = source.getProfile().getId();
        }
        UserDto userDto = new UserDto()
                .setId(source.getId())
                .setLogin(source.getLogin())
                .setEmail(source.getEmail())
                .setRole(source.getRole())
                .setProfileId(profileId)
                .setBankAccountIds(bankAccountIds);
        return userDto;
    }

    @Override
    public User fromCreateDto(RegisterUserDto source) {
        User user = new User()
                .setLogin(source.getLogin())
                .setEmail(source.getEmail())
                .setPassword(source.getPassword());
        return user;
    }

    @Override
    public User fromUpdateDto(UserUpdateDto source) {
        User user = new User()
                .setLogin(source.getLogin())
                .setEmail(source.getEmail())
                .setPassword(source.getPassword());
        return user;
    }

    @Override
    public User merge(User source, User target) {
        if (source.getLogin() != null && !source.getLogin().equals(target.getLogin())) {
            target.setLogin(source.getLogin());
        }
        if (source.getEmail() != null && !source.getEmail().equals(target.getEmail())) {
            target.setEmail(source.getEmail());
        }
        if (source.getPassword() != null && !source.getPassword().equals(target.getPassword())) {
            target.setPassword(source.getPassword());
        }
        return target;
    }
}
