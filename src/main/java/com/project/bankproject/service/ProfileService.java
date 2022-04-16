package com.project.bankproject.service;

import com.project.bankproject.domain.entity.Profile;

import java.util.UUID;

/**
 * interface ProfileService for class {@link Profile}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface ProfileService {
    /**
     * Method for finding Profile from DataBase.
     *
     * @param id - @id of Profile object
     * @return Profile from DataBase
     */
    Profile findById(UUID id);

    /**
     * Method for save created Profile in DataBase.
     * If Profile from current User exist, then return
     * old Profile.
     *
     * @return created Profile with balance = 0.0 and current User
     */
    Profile create(Profile profile);

    /**
     * Method for changing balance of Profile.
     *
     * @param number - number for adding to balance
     * @return updated Profile
     */
    Profile changeBalance(double number);

    /**
     * Method for updating Profile in DataBase.
     *
     * @return updated Profile
     */
    Profile update(Profile profile);
}
