package com.project.bankproject.unit.service.BankAccountServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.exception.BalanceException;
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
import static org.mockito.Mockito.when;

/**
 * class ChangeBalanceTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplChangeBalanceTest {
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    public void testBankAccountNotFound() {
        UUID id = UUID.randomUUID();
        double number = 1000.0D;
        when(bankAccountRepository.findById(id)).thenReturn(Optional.empty());
        try {
            bankAccountService.changeBalance(id, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("BankAccount with id: " + id + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testNumberEqualZero() {
        UUID id = UUID.randomUUID();
        double number = 0.0D;
        BankAccount bankAccount = new BankAccount().setBalance(123D);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));
        BankAccount result = bankAccountService.changeBalance(id, number);
        assertEquals(123D, result.getBalance());
    }

    @Test
    public void testNumberMoreThanBalance() {
        UUID id = UUID.randomUUID();
        double number = -124.0D;
        BankAccount bankAccount = new BankAccount().setBalance(123D);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));
        try {
            bankAccountService.changeBalance(id, number);
        } catch (BalanceException exception) {
            assertEquals("Not enough cash on the balance", exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        UUID id = UUID.randomUUID();
        double number = 100.0D;
        BankAccount bankAccount = new BankAccount().setBalance(123D);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        BankAccount result = bankAccountService.changeBalance(id, number);
        assertEquals(223D, result.getBalance());
    }
}
