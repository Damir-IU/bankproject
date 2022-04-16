package com.project.bankproject.unit.service.UserServiceImpl;

import com.project.bankproject.domain.entity.User;
import com.project.bankproject.repository.UserRepository;
import com.project.bankproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

/**
 * class FindAllTest
 *
 * @author damir.iusupov
 * @since 2022-04-10
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplFindAllTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testPositive() {
        User user1 = new User().setLogin("mockUser1");
        User user2 = new User().setLogin("mockUser2");
        User user3 = new User().setLogin("mockUser3");
        List<User> userList = List.of(user1, user2, user3);
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(userRepository.findAll(pageRequest)).thenReturn(new PageImpl<User>(userList));
        Page<User> result = userService.findAll(pageRequest);
        assertEquals(1, result.getTotalPages());
        assertEquals(3, result.getTotalElements());
        assertSame(user1, result.getContent().get(0));
        assertSame(user2, result.getContent().get(1));
        assertSame(user3, result.getContent().get(2));
    }
}
