package com.project.bankproject.service.impl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.BalanceException;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.ProfileRepository;
import com.project.bankproject.service.BankAccountService;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.ProfileService;
import com.project.bankproject.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of {@link TransferService} interface.
 * Plus business logic.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransferServiceImpl implements TransferService {
    private final ProfileService profileService;
    private final CurrentUserService currentUserService;
    private final BankAccountService bankAccountService;

    @Override
    @Transactional
    public Profile transferFromProfileToProfile(UUID toProfileId, double number) {
        final User user = currentUserService.currentUser();
        if (user.getProfile() == null) {
            throw new EntityNotFoundException("Profile NOT FOUND");
        }
        Profile source = user.getProfile();
        Profile target = profileService.findById(toProfileId);
        if (number > source.getBalance()) {
            throw new BalanceException("Not enough cash on the source Profile balance");
        }
        source.setBalance(source.getBalance() - number);
        target.setBalance(target.getBalance() + number);
        Profile result = profileService.update(source);
        profileService.update(target);
        return result;
    }

    @Override
    @Transactional
    public Profile transferFromProfileToBankAccount(UUID toBankAccountId, double number) {
        final User user = currentUserService.currentUser();
        if (user.getProfile() == null) {
            throw new EntityNotFoundException("Profile NOT FOUND");
        }
        Profile source = user.getProfile();
        BankAccount target = bankAccountService.findById(toBankAccountId);
        if (number > source.getBalance()) {
            throw new BalanceException("Not enough cash on the source Profile balance");
        }
        source.setBalance(source.getBalance() - number);
        target.setBalance(target.getBalance() + number);
        Profile result = profileService.update(source);
        bankAccountService.update(target);
        return result;
    }

    @Override
    @Transactional
    public BankAccount transferFromBankAccountToProfile(UUID fromBankAccountId, UUID toProfileId, double number) {
        BankAccount source = bankAccountService.findById(fromBankAccountId);
        Profile target = profileService.findById(toProfileId);
        if (number > source.getBalance()) {
            throw new BalanceException("Not enough cash on the source Bank Account balance");
        }
        source.setBalance(source.getBalance() - number);
        target.setBalance(target.getBalance() + number);
        BankAccount result = bankAccountService.update(source);
        profileService.update(target);
        return result;
    }

    @Override
    @Transactional
    public BankAccount transferFromBankAccountToBankAccount(UUID fromBankAccountId, UUID toBankAccountId, double number) {
        BankAccount source = bankAccountService.findById(fromBankAccountId);
        BankAccount target = bankAccountService.findById(toBankAccountId);
        if (number > source.getBalance()) {
            throw new BalanceException("Not enough cash on the source Bank Account balance");
        }
        source.setBalance(source.getBalance() - number);
        target.setBalance(target.getBalance() + number);
        BankAccount result = bankAccountService.update(source);
        bankAccountService.update(target);
        return result;
    }
}
