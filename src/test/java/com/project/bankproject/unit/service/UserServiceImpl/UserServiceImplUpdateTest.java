package com.project.bankproject.unit.service.UserServiceImpl;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.domain.mapper.UserMapper;
import com.project.bankproject.repository.UserRepository;
import com.project.bankproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static com.project.bankproject.domain.entity.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class UpdateTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplUpdateTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;

    @Test
    public void testNotFoundTargetUser() {
        UUID id = UUID.randomUUID();
        String login = "mockLogin";
        String password = "mockPassword";
        User target = new User();
        User source = new User().setLogin(login).setPassword(password);
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        try {
            userService.update(id, source);
        } catch (EntityNotFoundException exception) {
            assertEquals("User with id: " + id + " NOT FOUND", exception.getMessage());
            verify(userMapper, times(0)).merge(same(source), same(target));
            verify(passwordEncoder, times(0)).encode(same(source.getPassword()));
            verify(userRepository, times(0)).save(same(source));
        }
    }

    @Test
    public void testFoundTargetUser() {
        UUID id = UUID.randomUUID();
        String login = "mockLogin";
        String email = "mockEmail";
        String password = "mockPassword";
        User target = new User().setRole(ROLE_USER);
        User source = new User().setLogin(login).setEmail(email).setPassword(password);
        when(userRepository.findById(id)).thenReturn(Optional.of(target));
        when(userMapper.merge(source, target))
                .thenReturn(target.setLogin(source.getLogin()).setEmail(source.getEmail()).setPassword(source.getPassword()));
        when(passwordEncoder.encode(target.getPassword())).thenReturn("encodedMockPassword");
        when(userRepository.save(target)).thenReturn(target);
        User result = userService.update(id, source);
        assertEquals(login, result.getLogin());
        assertEquals(email, result.getEmail());
        assertEquals("encodedMockPassword", result.getPassword());
        assertEquals(ROLE_USER, result.getRole());
        assertEquals(0, result.getBankAccounts().size());
    }
}
