package com.project.bankproject.service.impl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.BalanceException;
import com.project.bankproject.domain.exception.BankAccountsException;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.BankAccountRepository;
import com.project.bankproject.service.BankAccountService;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * class BankAccountServiceImpl for create connections between BankAccountRepository
 * and BankAccountController. Implementation of {@link BankAccountService} interface.
 * Wrapper for {@link BankAccountRepository} and plus business logic.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CurrentUserService currentUserService;
    private final ProfileService profileService;

    @Override
    public BankAccount findById(UUID id) {
        BankAccount result = bankAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BankAccount with id: " + id + " NOT FOUND"));
        return result;
    }

    @Override
    @Transactional
    public BankAccount create() {
        final User user = currentUserService.currentUser();
        if (user.getBankAccounts().size() == 5) {
            throw new BankAccountsException("Count of Bank Accounts of user " + user.getLogin() + " is max");
        }
        BankAccount source = new BankAccount().setBalance(0.0D);
        user.addBankAccounts(source);
        BankAccount result = bankAccountRepository.save(source);
        return result;
    }

    @Override
    public List<BankAccount> findAllByUserId() {
        final User user = currentUserService.currentUser();
        List<BankAccount> result = bankAccountRepository.findAllByUserId(user.getId());
        return result;
    }

    @Override
    @Transactional
    public BankAccount changeBalance(UUID id, double number) {
        BankAccount target = this.findById(id);
        if (number == 0.0D) {
            return target;
        } else if (-number > target.getBalance()) {
            throw new BalanceException("Not enough cash on the balance");
        } else {
            double resultBalance = target.getBalance() + number;
            target.setBalance(resultBalance);
            BankAccount result = bankAccountRepository.save(target);
            return result;
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        final User user = currentUserService.currentUser();
        BankAccount targetBankAccount = this.findById(id);
        if (targetBankAccount.getBalance() != 0.0D) {
            if (user.getProfile() == null) {
                throw new EntityNotFoundException("Profile NOT FOUND and balance of Bank Account more than 0");
            }
            Profile targetProfile = profileService.findById(user.getProfile().getId());
            double resultBalance = targetBankAccount.getBalance() + targetProfile.getBalance();
            targetProfile.setBalance(resultBalance);
            profileService.update(targetProfile);
        }
        user.removeBankAccount(targetBankAccount);
        bankAccountRepository.delete(targetBankAccount);
    }

    @Override
    @Transactional
    public BankAccount update(BankAccount bankAccount) {
        BankAccount result = bankAccountRepository.save(bankAccount);
        return result;
    }
}
