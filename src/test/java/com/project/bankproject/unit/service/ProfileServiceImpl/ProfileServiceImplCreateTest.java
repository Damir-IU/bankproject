package com.project.bankproject.unit.service.ProfileServiceImpl;

import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.repository.ProfileRepository;
import com.project.bankproject.security.exception.JwtAuthenticationException;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class CreateTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplCreateTest {
    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private CurrentUserService currentUserService;

    @Test
    public void testNoAuth() {
        String firstName = "mockFirstName";
        String lastName = "mockLastName";
        Profile profile = new Profile().setFirstName(firstName).setLastName(lastName);
        when(currentUserService.currentUser())
                .thenThrow(new JwtAuthenticationException("JWT token is invalid or empty"));
        try {
            profileService.create(profile);
        } catch (JwtAuthenticationException exception) {
            assertEquals("JWT token is invalid or empty", exception.getMessage());
        }
    }

    @Test
    public void testProfileNotExist() {
        String login = "mockLogin";
        User user = new User().setLogin(login);
        String firstName = "mockFirstName";
        String lastName = "mockLastName";
        Profile profile = new Profile().setFirstName(firstName).setLastName(lastName);
        when(currentUserService.currentUser()).thenReturn(user);
        when(profileRepository.save(profile)).thenReturn(profile);
        Profile result = profileService.create(profile);
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(0.0D, result.getBalance());
        assertSame(user, result.getUser());
    }

    @Test
    public void testProfileExist() {
        String login = "mockLogin";
        User user = new User().setLogin(login);
        String firstName = "mockFirstName";
        String lastName = "mockLastName";
        Profile profile = new Profile().setFirstName(firstName).setLastName(lastName);
        user.setProfile(profile);
        profile.setUser(user);
        when(currentUserService.currentUser()).thenReturn(user);
        Profile result = profileService.create(profile);
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(0.0D, result.getBalance());
        assertSame(user, result.getUser());
        verify(profileRepository, times(0)).save(same(profile));
    }
}
