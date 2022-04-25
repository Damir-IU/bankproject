package com.project.bankproject.unit.service.TransferServiceImpl;

import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.BalanceException;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.security.exception.JwtAuthenticationException;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.ProfileService;
import com.project.bankproject.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * class TransferServiceImplTransferFromProfileToProfileTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class TransferServiceImplTransferFromProfileToProfileTest {
    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private ProfileService profileService;
    @Mock
    private CurrentUserService currentUserService;

    @Test
    public void testNoAuth() {
        UUID profileId = UUID.randomUUID();
        double number = 100D;
        when(currentUserService.currentUser())
                .thenThrow(new JwtAuthenticationException("JWT token is invalid or empty"));
        try {
            transferService.transferFromProfileToProfile(profileId, number);
        } catch (JwtAuthenticationException exception) {
            assertEquals("JWT token is invalid or empty", exception.getMessage());
            verify(profileService, times(0)).update(any(Profile.class));
        }
    }

    @Test
    public void testProfileFromCurrentUserNotFound() {
        UUID profileId = UUID.randomUUID();
        double number = 100D;
        String login = "mockLogin";
        User user = new User().setLogin(login);
        when(currentUserService.currentUser()).thenReturn(user);
        try {
            transferService.transferFromProfileToProfile(profileId, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("Profile NOT FOUND", exception.getMessage());
            verify(profileService, times(0)).update(any(Profile.class));
        }
    }

    @Test
    public void testTargetProfileNotFound() {
        UUID profileId = UUID.randomUUID();
        double number = 100D;
        User user = new User();
        Profile source = new Profile();
        user.setProfile(source);
        source.setUser(user);
        when(currentUserService.currentUser()).thenReturn(user);
        when(profileService.findById(profileId))
                .thenThrow(new EntityNotFoundException("Profile with id: " + profileId + " NOT FOUND"));
        try {
            transferService.transferFromProfileToProfile(profileId, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("Profile with id: " + profileId + " NOT FOUND", exception.getMessage());
            verify(profileService, times(0)).update(any(Profile.class));
        }
    }

    @Test
    public void testNumberMoreThanBalanceFromSourceProfile() {
        UUID profileId = UUID.randomUUID();
        double number = 100D;
        User user = new User();
        Profile source = new Profile().setBalance(50D);
        user.setProfile(source);
        source.setUser(user);
        Profile target = new Profile().setBalance(123D);
        when(currentUserService.currentUser()).thenReturn(user);
        when(profileService.findById(profileId)).thenReturn(target);
        try {
            transferService.transferFromProfileToProfile(profileId, number);
        } catch (BalanceException exception) {
            assertEquals("Not enough cash on the source Profile balance", exception.getMessage());
            verify(profileService, times(0)).update(any(Profile.class));
        }
    }

    @Test
    public void testPositive() {
        UUID profileId = UUID.randomUUID();
        double number = 100D;
        User user = new User();
        Profile source = new Profile().setBalance(150D);
        user.setProfile(source);
        source.setUser(user);
        Profile target = new Profile().setBalance(123D);
        when(currentUserService.currentUser()).thenReturn(user);
        when(profileService.findById(profileId)).thenReturn(target);
        when(profileService.update(source)).thenReturn(source);
        Profile result = transferService.transferFromProfileToProfile(profileId, number);
        assertEquals(50D, result.getBalance());
        assertEquals(223D, target.getBalance());
    }
}
