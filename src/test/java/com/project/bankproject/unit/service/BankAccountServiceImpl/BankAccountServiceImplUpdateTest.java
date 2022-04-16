package com.project.bankproject.unit.service.BankAccountServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.repository.BankAccountRepository;
import com.project.bankproject.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * class UpdateTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplUpdateTest {
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    public void testPositive() {
        BankAccount bankAccount = new BankAccount().setBalance(100D);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
        BankAccount result = bankAccountService.update(bankAccount);
        assertEquals(100D, result.getBalance());
    }
}
