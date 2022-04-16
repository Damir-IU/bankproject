package com.project.bankproject.unit.service.BankAccountServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.BankAccountRepository;
import com.project.bankproject.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

/**
 * class FindByIdTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplFindByIdTest {
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    public void testBankAccountNotFound() {
        UUID id = UUID.randomUUID();
        when(bankAccountRepository.findById(id)).thenReturn(Optional.empty());
        try {
            bankAccountService.findById(id);
        } catch (EntityNotFoundException exception) {
            assertEquals("BankAccount with id: " + id + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testBankAccountFound() {
        String login = "mockLogin";
        User user = new User().setLogin(login);
        UUID id = UUID.randomUUID();
        BankAccount bankAccount = new BankAccount().setBalance(0.0D);
        user.addBankAccounts(bankAccount);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));
        BankAccount result = bankAccountService.findById(id);
        assertEquals(0.0D, result.getBalance());
        assertSame(user, result.getUser());
    }
}
