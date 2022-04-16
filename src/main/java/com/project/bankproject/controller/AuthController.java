package com.project.bankproject.controller;

import com.project.bankproject.domain.dto.LoginDto;
import com.project.bankproject.domain.dto.RegisterUserDto;
import com.project.bankproject.domain.dto.UserDto;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.mapper.UserMapper;
import com.project.bankproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * class AuthController - RestController for class {@link User}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    /**
     * Method for auth User and getting token.
     *
     * @param loginDto
     * @return token for User
     */
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto.getLogin(), loginDto.getPassword());
        return token;
    }

    /**
     * Method for registration (saving) new User in DataBase.
     *
     * @param createDto - UserCreateDto object for create
     * @return created UserDto
     */
    @PostMapping("/sign-up")
    public UserDto register(@Valid @RequestBody RegisterUserDto createDto) {
        User source = userMapper.fromCreateDto(createDto);
        User target = authService.register(source);
        UserDto result = userMapper.toDto(target);
        return result;
    }
}
