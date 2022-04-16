package com.project.bankproject.unit.service.TransferServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.exception.BalanceException;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.service.BankAccountService;
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
 * class TranferServiceImplTransferFromBankAccountToBankAccountTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class TranferServiceImplTransferFromBankAccountToBankAccountTest {
    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private ProfileService profileService;
    @Mock
    private BankAccountService bankAccountService;

    @Test
    public void testBankAccountSourceNotFound() {
        UUID fromBankAccountId = UUID.randomUUID();
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        when(bankAccountService.findById(fromBankAccountId))
                .thenThrow(new EntityNotFoundException("BankAccount with id: " + fromBankAccountId + " NOT FOUND"));
        try {
            transferService.transferFromBankAccountToBankAccount(fromBankAccountId, toBankAccountId, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("BankAccount with id: " + fromBankAccountId + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testBankAccountTargetNotFound() {
        UUID fromBankAccountId = UUID.randomUUID();
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        BankAccount source = new BankAccount().setBalance(150D);
        when(bankAccountService.findById(fromBankAccountId)).thenReturn(source);
        when(bankAccountService.findById(toBankAccountId))
                .thenThrow(new EntityNotFoundException("BankAccount with id: " + toBankAccountId + " NOT FOUND"));
        try {
            transferService.transferFromBankAccountToBankAccount(fromBankAccountId, toBankAccountId, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("BankAccount with id: " + toBankAccountId + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testNumberMoreThanBalanceFromSourceBankAccount() {
        UUID fromBankAccountId = UUID.randomUUID();
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        BankAccount source = new BankAccount().setBalance(50D);
        BankAccount target = new BankAccount().setBalance(123D);
        when(bankAccountService.findById(fromBankAccountId)).thenReturn(source);
        when(bankAccountService.findById(toBankAccountId)).thenReturn(target);
        try {
            transferService.transferFromBankAccountToBankAccount(fromBankAccountId, toBankAccountId, number);
        } catch (BalanceException exception) {
            assertEquals("Not enough cash on the source Bank Account balance", exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        UUID fromBankAccountId = UUID.randomUUID();
        UUID toBankAccountId = UUID.randomUUID();
        double number = 100D;
        BankAccount source = new BankAccount().setBalance(150D);
        BankAccount target = new BankAccount().setBalance(123D);
        when(bankAccountService.findById(fromBankAccountId)).thenReturn(source);
        when(bankAccountService.findById(toBankAccountId)).thenReturn(target);
        when(bankAccountService.update(source)).thenReturn(source);
        BankAccount result = transferService.transferFromBankAccountToBankAccount(fromBankAccountId, toBankAccountId, number);
        assertEquals(50D, result.getBalance());
        assertEquals(223D, target.getBalance());
    }
}
