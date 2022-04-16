package com.project.bankproject.unit.service.UserServiceImpl;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.UserRepository;
import com.project.bankproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.project.bankproject.domain.entity.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * class FindByIdTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplFindByIdTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        try {
            userService.findById(id);
        } catch (EntityNotFoundException exception) {
            assertEquals("User with id: " + id + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testUserFound() {
        UUID id = UUID.randomUUID();
        String login = "mockLogin";
        String email = "mockEmail";
        String password = "mockPassword";
        User user = new User().setLogin(login).setEmail(email).setPassword(password).setRole(ROLE_USER);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        User result = userService.findById(id);
        assertEquals(login, result.getLogin());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        assertEquals(ROLE_USER, result.getRole());
        assertEquals(0, result.getBankAccounts().size());
    }
}
