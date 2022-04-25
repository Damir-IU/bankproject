package com.project.bankproject.service.impl;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.domain.mapper.UserMapper;
import com.project.bankproject.repository.UserRepository;
import com.project.bankproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * class UserServiceImpl for create connections between UserRepository and UserController.
 * Implementation of {@link UserService} interface.
 * Wrapper for {@link UserRepository} and plus business logic.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public User findById(UUID id) {
        User result = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " NOT FOUND"));
        return result;
    }

    @Override
    public User findByLogin(String login) {
        User result = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User with login: " + login + " NOT FOUND"));
        return result;
    }

    @Override
    @Transactional
    public User update(UUID id, User source) {
        User target = this.findById(id);
        User resultTarget = userMapper.merge(source, target);
        resultTarget.setPassword(passwordEncoder.encode(resultTarget.getPassword()));
        User result = userRepository.save(resultTarget);
        return result;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        Page<User> result = userRepository.findAll(pageable);
        return result;
    }
}
