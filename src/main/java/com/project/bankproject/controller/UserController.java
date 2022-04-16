package com.project.bankproject.controller;

import com.project.bankproject.domain.dto.UserDto;
import com.project.bankproject.domain.dto.UserUpdateDto;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.mapper.UserMapper;
import com.project.bankproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * class UserController - RestController for class {@link User}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Method for finding User with id from DataBase and mapping
     * to UserDto.
     *
     * @param id - id of User object
     * @return UserDto from DataBase
     */
    @GetMapping("/{id}")
    @PreAuthorize("@checkOwnerServiceImpl.ownerUser(#id)")
    public UserDto findById(@PathVariable UUID id) {
        User target = userService.findById(id);
        UserDto result = userMapper.toDto(target);
        return result;
    }


    /**
     * Method for replacing the old User with new one and
     * saving new User in DataBase.
     *
     * @param id        - id of User object
     * @param updateDto - UserUpdateDto object for merge-update
     * @return updated UserDto
     */
    @PatchMapping("/{id}")
    @PreAuthorize("@checkOwnerServiceImpl.ownerUser(#id)")
    public UserDto update(@PathVariable UUID id,
                          @Valid @RequestBody UserUpdateDto updateDto) {
        User source = userMapper.fromUpdateDto(updateDto);
        User target = userService.update(id, source);
        UserDto result = userMapper.toDto(target);
        return result;
    }


    /**
     * Method for displaying the Page of UserDto.
     * Default link: "users" == "users?page=0&size=10&sort=name,asc".
     * Params of this link can be changed:
     * page=0 - number of Page;
     * size=10 - number of elements on the Page;
     * sort=name,asc - sort by field of User and by ascending (asc) or descending (desc).
     *
     * @param pageable - param for pagination information
     * @return Page of UserDto
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<UserDto> findAll(Pageable pageable) {
        Page<UserDto> result = userService
                .findAll(pageable)
                .map(userMapper::toDto);
        return result;
    }
}
