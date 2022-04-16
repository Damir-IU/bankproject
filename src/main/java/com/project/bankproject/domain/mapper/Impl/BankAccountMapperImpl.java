package com.project.bankproject.domain.mapper.Impl;

import com.project.bankproject.domain.dto.BankAccountDto;
import com.project.bankproject.domain.entity.BankAccount;
import com.project.bankproject.domain.mapper.BankAccountMapper;
import org.springframework.stereotype.Component;

/**
 * class BankAccountMapperImpl implementation of {@link BankAccountMapper} interface.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Component
public class BankAccountMapperImpl implements BankAccountMapper {
    @Override
    public BankAccountDto toDto(BankAccount source) {
        BankAccountDto bankAccountDto = new BankAccountDto()
                .setId(source.getId())
                .setBalance(source.getBalance());
        return bankAccountDto;
    }
}
