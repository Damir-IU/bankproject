package com.project.bankproject.controller;

import com.project.bankproject.domain.dto.ProfileCreateDto;
import com.project.bankproject.domain.dto.ProfileDto;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.mapper.ProfileMapper;
import com.project.bankproject.service.ProfileService;
import com.project.bankproject.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * class ProfileController - RestController for class {@link Profile}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final TransferService transferService;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    /**
     * Method for finding Profile with id from DataBase and mapping
     * to ProfileDto.
     *
     * @param id - id of Profile object
     * @return ProfileDto from DataBase
     */
    @GetMapping("/{id}")
    @PreAuthorize("@checkOwnerServiceImpl.ownerProfile(#id)")
    public ProfileDto findById(@PathVariable UUID id) {
        Profile target = profileService.findById(id);
        ProfileDto result = profileMapper.toDto(target);
        return result;
    }

    /**
     * Method for saving Profile in DataBase.
     *
     * @return created ProfileDto from DataBase
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ProfileDto create(@RequestBody @Valid ProfileCreateDto profileCreateDto) {
        Profile source = profileMapper.fromCreateDto(profileCreateDto);
        Profile target = profileService.create(source);
        ProfileDto result = profileMapper.toDto(target);
        return result;
    }

    /**
     * Method for changing balance of Profile.
     *
     * @param number - number for changing balance
     * @return updated Profile
     */
    @PatchMapping("/{number}")
    @PreAuthorize("hasAnyRole('USER')")
    public ProfileDto changeBalance(@PathVariable double number) {
        Profile target = profileService.changeBalance(number);
        ProfileDto result = profileMapper.toDto(target);
        return result;
    }

    /**
     * Method for transferring balance from Profile to another Profile.
     *
     * @param toProfileId - id of target Profile
     * @param number      - number for transferring balance
     * @return updated Profile
     */
    @PatchMapping("/transfer-profile/{toProfileId}/{number}")
    @PreAuthorize("hasAnyRole('USER')")
    public ProfileDto transferFromProfileToProfile(@PathVariable UUID toProfileId,
                                                   @PathVariable double number) {
        Profile target = transferService.transferFromProfileToProfile(toProfileId, number);
        ProfileDto result = profileMapper.toDto(target);
        return result;
    }

    /**
     * Method for transferring balance from Profile to BankAccount.
     *
     * @param toBankAccountId - id of target BankAccount
     * @param number          - number for transferring balance
     * @return updated Profile
     */
    @PatchMapping("/transfer-bank-account/{toBankAccountId}/{number}")
    @PreAuthorize("hasAnyRole('USER')")
    public ProfileDto transferFromProfileToBankAccount(@PathVariable UUID toBankAccountId,
                                                       @PathVariable double number) {
        Profile target = transferService.transferFromProfileToBankAccount(toBankAccountId, number);
        ProfileDto result = profileMapper.toDto(target);
        return result;
    }
}
