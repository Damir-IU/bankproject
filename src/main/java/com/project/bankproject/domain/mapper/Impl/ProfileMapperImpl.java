package com.project.bankproject.domain.mapper.Impl;

import com.project.bankproject.domain.dto.ProfileCreateDto;
import com.project.bankproject.domain.dto.ProfileDto;
import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.mapper.ProfileMapper;
import org.springframework.stereotype.Component;

/**
 * class ProfileMapperImpl implementation of {@link ProfileMapper} interface.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Component
public class ProfileMapperImpl implements ProfileMapper {
    @Override
    public ProfileDto toDto(Profile source) {
        ProfileDto profileDto = new ProfileDto()
                .setId(source.getId())
                .setFirstName(source.getFirstName())
                .setLastName(source.getLastName())
                .setBalance(source.getBalance())
                .setUserId(source.getUser().getId());
        return profileDto;
    }

    @Override
    public Profile fromCreateDto(ProfileCreateDto source) {
        Profile profile = new Profile()
                .setFirstName(source.getFirstName())
                .setLastName(source.getLastName());
        return profile;
    }
}
