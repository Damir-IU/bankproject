package com.project.bankproject.unit.service.AuthServiceImpl;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.UserRepository;
import com.project.bankproject.service.TokenService;
import com.project.bankproject.service.UserService;
import com.project.bankproject.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class LoginTest
 *
 * @author damir.iusupov
 * @since 2022-04-10
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplLoginTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;

    @Test
    public void testUserNotFoundByLogin() {
        String login = "mockLogin";
        String password = "mockPassword";
        User user = new User().setLogin(login).setPassword(password);
        when(userService.findByLogin(login))
                .thenThrow(new EntityNotFoundException("User with login: " + login + " NOT FOUND"));
        try {
            authService.login(login, password);
        } catch (EntityNotFoundException exception) {
            assertEquals("User with login: " + login + " NOT FOUND", exception.getMessage());
            verify(passwordEncoder, times(0)).matches(anyString(), anyString());
            verify(tokenService, times(0)).generateToken(any(User.class));
        }
    }

    @Test
    public void testMatchPasswordTrue() {
        String login = "mockLogin";
        String password = "mockPassword";
        User user = new User().setLogin(login).setPassword(password);
        String token = "mockToken";
        when(userService.findByLogin(login)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn(token);
        String result = authService.login(login, password);
        assertEquals(token, result);
    }

    @Test
    public void testMatchPasswordFalse() {
        String login = "mockLogin";
        String password = "mockPassword";
        User user = new User().setLogin(login).setPassword(password);
        when(userService.findByLogin(login)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);
        try {
            authService.login(login, password);
        } catch (EntityNotFoundException exception) {
            assertEquals("Password is not correct", exception.getMessage());
            verify(tokenService, times(0)).generateToken(any(User.class));
        }
    }
}
