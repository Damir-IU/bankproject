package com.project.bankproject.unit.service.BankAccountServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.repository.BankAccountRepository;
import com.project.bankproject.security.exception.JwtAuthenticationException;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

/**
 * class FindAllByUserIdTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplFindAllByUserIdTest {
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
            bankAccountService.findAllByUserId();
        } catch (JwtAuthenticationException exception) {
            assertEquals("JWT token is invalid or empty", exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        String login = "mockLogin";
        BankAccount bankAccount1 = new BankAccount().setBalance(1.0D);
        BankAccount bankAccount2 = new BankAccount().setBalance(2.0D);
        BankAccount bankAccount3 = new BankAccount().setBalance(3.0D);
        User user = new User().setLogin(login);
        user.addBankAccounts(bankAccount1);
        user.addBankAccounts(bankAccount2);
        user.addBankAccounts(bankAccount3);
        List<BankAccount> bankAccounts = List.of(bankAccount1, bankAccount2, bankAccount3);
        when(currentUserService.currentUser()).thenReturn(user);
        when(bankAccountRepository.findAllByUserId(user.getId())).thenReturn(bankAccounts);
        List<BankAccount> result = bankAccountService.findAllByUserId();
        assertEquals(3, result.size());
        assertEquals(1.0D, result.get(0).getBalance());
        assertSame(user, result.get(0).getUser());
        assertEquals(2.0D, result.get(1).getBalance());
        assertSame(user, result.get(1).getUser());
        assertEquals(3.0D, result.get(2).getBalance());
        assertSame(user, result.get(2).getUser());
    }
}
