package com.project.bankproject.service.impl;

import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.domain.exception.BalanceException;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.ProfileRepository;
import com.project.bankproject.service.CurrentUserService;
import com.project.bankproject.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * class ProfileServiceImpl for create connections between ProfileRepository
 * and ProfileController. Implementation of {@link ProfileService} interface.
 * Wrapper for {@link ProfileRepository} and plus business logic.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final CurrentUserService currentUserService;

    @Override
    public Profile findById(UUID id) {
        Profile result = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile with id: " + id + " NOT FOUND"));
        return result;
    }

    @Override
    @Transactional
    public Profile create(Profile source) {
        final User user = currentUserService.currentUser();
        if (user.getProfile() == null) {
            source.setBalance(0.0D)
                    .setUser(user);
            Profile result = profileRepository.save(source);
            return result;
        } else {
            Profile result = user.getProfile();
            return result;
        }
    }

    @Override
    @Transactional
    public Profile changeBalance(double number) {
        final User user = currentUserService.currentUser();
        if (user.getProfile() == null) {
            throw new EntityNotFoundException("Profile NOT FOUND");
        }
        Profile target = user.getProfile();
        if (number == 0.0D) {
            return target;
        } else if (-number > target.getBalance()) {
            throw new BalanceException("Not enough cash on the Profile balance");
        } else {
            double resultBalance = target.getBalance() + number;
            target.setBalance(resultBalance);
            Profile result = profileRepository.save(target);
            return result;
        }
    }

    @Override
    @Transactional
    public Profile update(Profile profile) {
        Profile result = profileRepository.save(profile);
        return result;
    }
}
