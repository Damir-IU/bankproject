package com.project.bankproject.unit.service.BankAccountServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.BankAccountsException;
import com.project.bankproject.repository.BankAccountRepository;
import com.project.bankproject.security.exception.JwtAuthenticationException;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

/**
 * class CreateTest
 *
 * @author damir.iusupov
 * @since 2022-04-13
 */
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplCreateTest {
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private CurrentUserService currentUserService;

    @Test
    public void testNoAuth() {
        when(currentUserService.currentUser())
                .thenThrow(new JwtAuthenticationException("JWT token is invalid or empty"));
        try {
            bankAccountService.create();
        } catch (JwtAuthenticationException exception) {
            assertEquals("JWT token is invalid or empty", exception.getMessage());
        }
    }

    @Test
    public void testCountMoreThanFive() {
        String login = "mockLogin";
        BankAccount bankAccount1 = new BankAccount().setBalance(1.0D);
        BankAccount bankAccount2 = new BankAccount().setBalance(2.0D);
        BankAccount bankAccount3 = new BankAccount().setBalance(3.0D);
        BankAccount bankAccount4 = new BankAccount().setBalance(4.0D);
        BankAccount bankAccount5 = new BankAccount().setBalance(5.0D);
        User user = new User().setLogin(login);
        user.addBankAccounts(bankAccount1);
        user.addBankAccounts(bankAccount2);
        user.addBankAccounts(bankAccount3);
        user.addBankAccounts(bankAccount4);
        user.addBankAccounts(bankAccount5);
        when(currentUserService.currentUser()).thenReturn(user);
        try {
            bankAccountService.create();
        } catch (BankAccountsException exception) {
            assertEquals("Count of Bank Accounts of user " + user.getLogin() + " is max", exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        String login = "mockLogin";
        User user = new User().setLogin(login);
        when(currentUserService.currentUser()).thenReturn(user);
        BankAccount source = new BankAccount().setBalance(0.0D);
        user.addBankAccounts(source);
        when(bankAccountRepository.save(source)).thenReturn(source);
        BankAccount result = bankAccountService.create();
        assertSame(user, result.getUser());
        assertEquals(0.0D, result.getBalance());
    }
}
