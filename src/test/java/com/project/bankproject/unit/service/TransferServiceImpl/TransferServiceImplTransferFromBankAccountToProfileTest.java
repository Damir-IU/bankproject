package com.project.bankproject.unit.service.TransferServiceImpl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.exception.BalanceException;
import com.project.bankproject.domain.exception.EntityNotFoundException;
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
 * class TransferServiceImplTransferFromBankAccountToProfileTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class TransferServiceImplTransferFromBankAccountToProfileTest {
    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private ProfileService profileService;
    @Mock
    private BankAccountService bankAccountService;

    @Test
    public void testBankAccountSourceNotFound() {
        UUID fromBankAccountId = UUID.randomUUID();
        UUID toProfileId = UUID.randomUUID();
        double number = 100D;
        when(bankAccountService.findById(fromBankAccountId))
                .thenThrow(new EntityNotFoundException("BankAccount with id: " + fromBankAccountId + " NOT FOUND"));
        try {
            transferService.transferFromBankAccountToProfile(fromBankAccountId, toProfileId, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("BankAccount with id: " + fromBankAccountId + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testProfileTargetNotFound() {
        UUID fromBankAccountId = UUID.randomUUID();
        UUID toProfileId = UUID.randomUUID();
        double number = 100D;
        BankAccount source = new BankAccount().setBalance(150D);
        when(bankAccountService.findById(fromBankAccountId)).thenReturn(source);
        when(profileService.findById(toProfileId))
                .thenThrow(new EntityNotFoundException("Profile with id: " + toProfileId + " NOT FOUND"));
        try {
            transferService.transferFromBankAccountToProfile(fromBankAccountId, toProfileId, number);
        } catch (EntityNotFoundException exception) {
            assertEquals("Profile with id: " + toProfileId + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testNumberMoreThanBalanceFromSourceBankAccount() {
        UUID fromBankAccountId = UUID.randomUUID();
        UUID toProfileId = UUID.randomUUID();
        double number = 100D;
        BankAccount source = new BankAccount().setBalance(50D);
        Profile target = new Profile().setBalance(123D);
        when(bankAccountService.findById(fromBankAccountId)).thenReturn(source);
        when(profileService.findById(toProfileId)).thenReturn(target);
        try {
            transferService.transferFromBankAccountToProfile(fromBankAccountId, toProfileId, number);
        } catch (BalanceException exception) {
            assertEquals("Not enough cash on the source Bank Account balance", exception.getMessage());
        }
    }

    @Test
    public void testPositive() {
        UUID fromBankAccountId = UUID.randomUUID();
        UUID toProfileId = UUID.randomUUID();
        double number = 100D;
        BankAccount source = new BankAccount().setBalance(150D);
        Profile target = new Profile().setBalance(123D);
        when(bankAccountService.findById(fromBankAccountId)).thenReturn(source);
        when(profileService.findById(toProfileId)).thenReturn(target);
        when(bankAccountService.update(source)).thenReturn(source);
        when(profileService.update(target)).thenReturn(target);
        BankAccount result = transferService.transferFromBankAccountToProfile(fromBankAccountId, toProfileId, number);
        assertEquals(50D, result.getBalance());
        assertEquals(223D, target.getBalance());
    }
}
