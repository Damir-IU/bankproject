package com.project.bankproject.service.impl;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.UserRepository;
import com.project.bankproject.service.AuthService;
import com.project.bankproject.service.TokenService;
import com.project.bankproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.bankproject.domain.entity.Role.ROLE_USER;

/**
 * class AuthServiceImpl for create connections between UserRepository and AuthController.
 * Implementation of {@link AuthService} interface.
 * Wrapper for {@link UserRepository} and plus business logic.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public String login(String login, String password) {
        User user = userService.findByLogin(login);
        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = tokenService.generateToken(user);
            return token;
        } else {
            throw new EntityNotFoundException("Password is not correct");
        }
    }

    @Override
    @Transactional
    public User register(User user) {
        user.setRole(ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        return result;
    }
}
