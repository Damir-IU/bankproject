package com.project.bankproject.service.impl;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.security.exception.JwtAuthenticationException;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class CurrentUserServiceImpl implementation of {@link CurrentUserService} interface.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurrentUserServiceImpl implements CurrentUserService {
    private final UserService userService;

    @Override
    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals("anonymousUser")) {
            throw new JwtAuthenticationException("JWT token is invalid or empty");
        }
        String name = ((User) auth.getPrincipal()).getLogin();
        User result = userService.findByLogin(name);
        return result;
    }
}
