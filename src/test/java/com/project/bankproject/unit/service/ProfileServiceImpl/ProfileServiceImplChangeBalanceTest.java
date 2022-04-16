package com.project.bankproject.unit.service.ProfileServiceImpl;

import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.BalanceException;
import com.project.bankproject.domain.exception.EntityNotFoundException;
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
 * class ChangeBalanceTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplChangeBalanceTest {
    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private CurrentUserService currentUserService;


    @Test
    public void testNoAuth() {
        double number = 100D;
        when(currentUserService.currentUser())
                .thenThrow(new JwtAuthenticationException("JWT token is invalid or empty"));
        try {
            profileService.changeBalance(number);
        } catch (JwtAuthenticationException exception) {
            assertEquals("JWT token is invalid or empty", exception.getMessage());
        }
    }

    @Test
    public void testProfileNotFound() {
        double number = 100D;
        String login = "mockLogin";
        User user = new User().setLogin(login);
        when(currentUserService.currentUser()).thenReturn(user);
        try {
            profileService.changeBalance(number);
        } catch (EntityNotFoundException exception) {
            assertEquals("Profile NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testNumberEqualZero() {
        double number = 0D;
        String login = "mockLogin";
        User user = new User().setLogin(login);
        String firstName = "mockFirstName";
        String lastName = "mockLastName";
        Profile profile = new Profile().setFirstName(firstName).setLastName(lastName);
        user.setProfile(profile);
        profile.setUser(user);
        when(currentUserService.currentUser()).thenReturn(user);
        Profile result = profileService.changeBalance(number);
        assertEquals(0D, result.getBalance());
        assertSame(user, result.getUser());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        verify(profileRepository, times(0)).save(same(profile));
    }

    @Test
    public void testNumberMoreThanBalance() {
        double number = -124D;
        String login = "mockLogin";
        User user = new User().setLogin(login);
        String firstName = "mockFirstName";
        String lastName = "mockLastName";
        Profile profile = new Profile().setFirstName(firstName).setLastName(lastName).setBalance(123D);
        user.setProfile(profile);
        profile.setUser(user);
        when(currentUserService.currentUser()).thenReturn(user);
        try {
            profileService.changeBalance(number);
        } catch (BalanceException exception) {
            assertEquals("Not enough cash on the Profile balance", exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        double number = 100D;
        String login = "mockLogin";
        User user = new User().setLogin(login);
        String firstName = "mockFirstName";
        String lastName = "mockLastName";
        Profile profile = new Profile().setFirstName(firstName).setLastName(lastName).setBalance(123D);
        user.setProfile(profile);
        profile.setUser(user);
        when(currentUserService.currentUser()).thenReturn(user);
        when(profileRepository.save(profile)).thenReturn(profile);
        Profile result = profileService.changeBalance(number);
        assertEquals(223D, result.getBalance());
        assertSame(user, result.getUser());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
    }
}
