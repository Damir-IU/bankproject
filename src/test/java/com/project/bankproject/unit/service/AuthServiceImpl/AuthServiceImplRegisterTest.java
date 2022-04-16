package com.project.bankproject.unit.service.AuthServiceImpl;

import com.project.bankproject.domain.entity.User;
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

import static com.project.bankproject.domain.entity.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * class RegisterTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplRegisterTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPositive() {
        String login = "mockLogin";
        String email = "mockEmail";
        String password = "mockPassword";
        User user = new User().setLogin(login).setEmail(email).setPassword(password);
        when(userRepository.save(user)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedMockPassword");
        User result = authService.register(user);
        assertEquals(login, result.getLogin());
        assertEquals(email, result.getEmail());
        assertEquals("encodedMockPassword", result.getPassword());
        assertEquals(ROLE_USER, result.getRole());
        assertEquals(0, result.getBankAccounts().size());
    }
}
