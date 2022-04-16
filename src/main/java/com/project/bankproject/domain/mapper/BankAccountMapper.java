package com.project.bankproject.domain.mapper;

import com.project.bankproject.domain.dto.BankAccountDto;
import com.project.bankproject.domain.entity.BankAccount;

/**
 * interface BankAccountMapper for mapping Dto layer and for class {@link BankAccount}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface BankAccountMapper {
    /**
     * Method for mapping BankAccount to BankAccountDto.
     *
     * @param source - BankAccount object for mapping
     * @return BankAccountDto object
     */
    BankAccountDto toDto(BankAccount source);
}
