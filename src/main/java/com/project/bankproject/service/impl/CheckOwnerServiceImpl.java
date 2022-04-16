package com.project.bankproject.service.impl;

import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.service.BankAccountService;
import com.project.bankproject.service.CheckOwnerService;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.ProfileService;
import com.project.bankproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * class CurrentUserServiceImpl implementation of {@link CheckOwnerService} interface.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckOwnerServiceImpl implements CheckOwnerService {
    private final CurrentUserService currentUserService;
    private final BankAccountService bankAccountService;
    private final ProfileService profileService;
    private final UserService userService;

    @Override
    public boolean ownerUser(UUID userId) {
        User currentUser = currentUserService.currentUser();
        User targetUser = userService.findById(userId);
        boolean result = targetUser.equals(currentUser);
        return result;
    }

    @Override
    public boolean ownerProfile(UUID profileId) {
        User targetUser = currentUserService.currentUser();
        Profile targetProfile = profileService.findById(profileId);
        boolean result = targetProfile.getUser().equals(targetUser);
        return result;
    }

    @Override
    public boolean ownerBankAccount(UUID bankAccountId) {
        User targetUser = currentUserService.currentUser();
        BankAccount targetBankAccount = bankAccountService.findById(bankAccountId);
        boolean result = targetBankAccount.getUser().equals(targetUser);
        return result;
    }
}
