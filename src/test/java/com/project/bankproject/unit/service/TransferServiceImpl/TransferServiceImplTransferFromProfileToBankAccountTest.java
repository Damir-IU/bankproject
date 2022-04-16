package com.project.bankproject.unit.service.TransferServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.BalanceException;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.security.exception.JwtAuthenticationException;
import com.project.bankproject.service.BankAccountService;
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
import static org.mockito.Mockito.when;

/**
 * class TransferServiceImplTransferFromProfileToBankAccountTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class TransferServiceImplTransferFromProfileToBankAccountTest {
    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private ProfileService profileService;
    @Mock
    private CurrentUserService currentUserService;
    @Mock
    private BankAccountService bankAccountService;

    @Test
    public void testNoAuth() {
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        when(currentUserService.currentUser())
                .thenThrow(new JwtAuthenticationException("JWT token is invalid or empty"));
        try {
            transferService.transferFromProfileToBankAccount(toBankAccountId, number);
        } catch (JwtAuthenticationException exception) {
            assertEquals("JWT token is invalid or empty", exception.getMessage());
        }
    }

    @Test
    public void testProfileFromCurrentUserNotFound() {
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        String login = "mockLogin";
        User user = new User().setLogin(login);
        when(currentUserService.currentUser()).thenReturn(user);
        try {
            transferService.transferFromProfileToBankAccount(toBankAccountId, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("Profile NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testTargetProfileNotFound() {
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        User user = new User();
        Profile source = new Profile();
        user.setProfile(source);
        source.setUser(user);
        when(currentUserService.currentUser()).thenReturn(user);
        when(bankAccountService.findById(toBankAccountId))
                .thenThrow(new EntityNotFoundException("BankAccount with id: " + toBankAccountId + " NOT FOUND"));
        try {
            transferService.transferFromProfileToBankAccount(toBankAccountId, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("BankAccount with id: " + toBankAccountId + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testNumberMoreThanBalanceFromSourceProfile() {
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        User user = new User();
        Profile source = new Profile().setBalance(50D);
        user.setProfile(source);
        source.setUser(user);
        BankAccount target = new BankAccount().setBalance(123D);
        when(currentUserService.currentUser()).thenReturn(user);
        when(bankAccountService.findById(toBankAccountId)).thenReturn(target);
        try {
            transferService.transferFromProfileToBankAccount(toBankAccountId, number);
        } catch (BalanceException exception) {
            assertEquals("Not enough cash on the source Profile balance", exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        User user = new User();
        Profile source = new Profile().setBalance(150D);
        user.setProfile(source);
        source.setUser(user);
        BankAccount target = new BankAccount().setBalance(123D);
        when(currentUserService.currentUser()).thenReturn(user);
        when(bankAccountService.findById(toBankAccountId)).thenReturn(target);
        when(profileService.update(source)).thenReturn(source);
        when(bankAccountService.update(target)).thenReturn(target);
        Profile result = transferService.transferFromProfileToBankAccount(toBankAccountId,number);
        assertEquals(50D, result.getBalance());
        assertEquals(223D, target.getBalance());
    }
}
