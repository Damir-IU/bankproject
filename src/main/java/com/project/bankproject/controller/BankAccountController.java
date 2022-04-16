package com.project.bankproject.controller;

import com.project.bankproject.domain.dto.BankAccountDto;
import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.mapper.BankAccountMapper;
import com.project.bankproject.service.BankAccountService;
import com.project.bankproject.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * class BankAccountController - RestController for class {@link BankAccount}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-accounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;
    private final TransferService transferService;

    /**
     * Method for finding BankAccount with id from DataBase and mapping
     * to BankAccountDto.
     *
     * @param id - id of BankAccount object
     * @return BankAccountDto from DataBase
     */
    @GetMapping("/{id}")
    @PreAuthorize("@checkOwnerServiceImpl.ownerBankAccount(#id)")
    public BankAccountDto findById(@PathVariable UUID id) {
        BankAccount target = bankAccountService.findById(id);
        BankAccountDto result = bankAccountMapper.toDto(target);
        return result;
    }

    /**
     * Method for saving BankAccount in DataBase.
     *
     * @return created BankAccountDto from DataBase
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public BankAccountDto create() {
        BankAccount target = bankAccountService.create();
        BankAccountDto result = bankAccountMapper.toDto(target);
        return result;
    }

    /**
     * Method for finding all Bank Accounts from current User.
     *
     * @return list of BankAccounts
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public List<BankAccountDto> findAll() {
        List<BankAccount> target = bankAccountService.findAllByUserId();
        List<BankAccountDto> result = target
                .stream()
                .map(bankAccountMapper::toDto)
                .collect(Collectors.toList());
        return result;
    }

    /**
     * Method for changing balance of BankAccount.
     *
     * @param id     - id of BankAccount
     * @param number - number for changing balance
     * @return updated BankAccount
     */
    @PatchMapping("/{id}/{number}")
    @PreAuthorize("@checkOwnerServiceImpl.ownerBankAccount(#id)")
    public BankAccountDto changeBalance(@PathVariable UUID id, @PathVariable double number) {
        BankAccount target = bankAccountService.changeBalance(id, number);
        BankAccountDto result = bankAccountMapper.toDto(target);
        return result;
    }

    /**
     * Method for deleting BankAccount.
     *
     * @param id - id of BankAccount
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@checkOwnerServiceImpl.ownerBankAccount(#id)")
    public void changeBalance(@PathVariable UUID id) {
        bankAccountService.delete(id);
    }

    /**
     * Method for transferring balance from BankAccount to Profile.
     *
     * @param fromBankAccountId - id of source BankAccount
     * @param toProfileId       - id of target Profile
     * @param number            - number for transferring balance
     * @return updated BankAccount
     */
    @PatchMapping("/transfer-profile/{fromBankAccountId}/{toProfileId}/{number}")
    @PreAuthorize("@checkOwnerServiceImpl.ownerBankAccount(#fromBankAccountId)")
    public BankAccountDto transferFromBankAccountToProfile(@PathVariable UUID fromBankAccountId,
                                                           @PathVariable UUID toProfileId,
                                                           @PathVariable double number) {
        BankAccount target = transferService.transferFromBankAccountToProfile(fromBankAccountId, toProfileId, number);
        BankAccountDto result = bankAccountMapper.toDto(target);
        return result;
    }

    /**
     * Method for transferring balance from BankAccount to another BankAccount.
     *
     * @param fromBankAccountId - id of source BankAccount
     * @param toBankAccountId   - id of target BankAccount
     * @param number            - number for transferring balance
     * @return updated BankAccount
     */
    @PatchMapping("/transfer-bank-account/{fromBankAccountId}/{toBankAccountId}/{number}")
    @PreAuthorize("@checkOwnerServiceImpl.ownerBankAccount(#fromBankAccountId)")
    public BankAccountDto transferFromBankAccountToBankAccount(@PathVariable UUID fromBankAccountId,
                                                               @PathVariable UUID toBankAccountId,
                                                               @PathVariable double number) {
        BankAccount target = transferService.transferFromBankAccountToBankAccount(fromBankAccountId, toBankAccountId, number);
        BankAccountDto result = bankAccountMapper.toDto(target);
        return result;
    }
}
