package com.project.bankproject.domain.mapper;

import com.project.bankproject.domain.dto.ProfileCreateDto;
import com.project.bankproject.domain.dto.ProfileDto;
import com.project.bankproject.domain.entity.Profile;

/**
 * interface ProfileMapper for mapping Dto layer and for class {@link Profile}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface ProfileMapper {
    /**
     * Method for mapping Profile to ProfileDto.
     *
     * @param source - Profile object for mapping
     * @return ProfileDto object
     */
    ProfileDto toDto(Profile source);

    /**
     * Method for mapping ProfileCreateDto to Profile.
     *
     * @param source - ProfileCreateDto object for mapping
     * @return Profile object
     */
    Profile fromCreateDto(ProfileCreateDto source);
}
