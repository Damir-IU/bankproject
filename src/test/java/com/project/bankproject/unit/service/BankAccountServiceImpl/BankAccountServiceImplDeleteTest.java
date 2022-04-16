package com.project.bankproject.unit.service.BankAccountServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.BankAccountRepository;
import com.project.bankproject.security.exception.JwtAuthenticationException;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.ProfileService;
import com.project.bankproject.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * class DeleteTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplDeleteTest {
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private CurrentUserService currentUserService;
    @Mock
    private ProfileService profileService;

    @Test
    public void testNoAuth() {
        UUID id = UUID.randomUUID();
        when(currentUserService.currentUser())
                .thenThrow(new JwtAuthenticationException("JWT token is invalid or empty"));
        try {
            bankAccountService.delete(id);
        } catch (JwtAuthenticationException exception) {
            assertEquals("JWT token is invalid or empty", exception.getMessage());
        }
    }

    @Test
    public void testNotFoundBankAccount() {
        UUID id = UUID.randomUUID();
        String login = "mockLogin";
        User user = new User().setLogin(login);
        when(currentUserService.currentUser()).thenReturn(user);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.empty());
        try {
            bankAccountService.delete(id);
        } catch (EntityNotFoundException exception) {
            assertEquals("BankAccount with id: " + id + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testBalanceEqualZero() {
        UUID id = UUID.randomUUID();
        String login = "mockLogin";
        User user = new User().setLogin(login);
        BankAccount bankAccount = new BankAccount().setBalance(0D);
        user.addBankAccounts(bankAccount);
        when(currentUserService.currentUser()).thenReturn(user);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));
        bankAccountService.delete(id);
        assertEquals(0, user.getBankAccounts().size());
    }


    @Test
    public void testBalanceNotEqualZeroProfileNotFound() {
        UUID id = UUID.randomUUID();
        String login = "mockLogin";
        User user = new User().setLogin(login);
        BankAccount bankAccount = new BankAccount().setBalance(123D);
        user.addBankAccounts(bankAccount);
        when(currentUserService.currentUser()).thenReturn(user);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));
        try {
            bankAccountService.delete(id);
        } catch (EntityNotFoundException exception) {
            assertEquals("Profile NOT FOUND and balance of Bank Account more than 0", exception.getMessage());
        }
    }

    @Test
    public void testBalanceNotEqualZeroProfileFound() {
        UUID id = UUID.randomUUID();
        String login = "mockLogin";
        User user = new User().setLogin(login);
        Profile profile = new Profile().setBalance(100D);
        BankAccount bankAccount = new BankAccount().setBalance(123D);
        user.setProfile(profile);
        user.addBankAccounts(bankAccount);
        when(currentUserService.currentUser()).thenReturn(user);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));
        when(profileService.findById(user.getProfile().getId())).thenReturn(profile);
        when(profileService.update(profile)).thenReturn(profile);
        bankAccountService.delete(id);
        assertEquals(223D, profile.getBalance());
        assertEquals(0, user.getBankAccounts().size());
    }
}
