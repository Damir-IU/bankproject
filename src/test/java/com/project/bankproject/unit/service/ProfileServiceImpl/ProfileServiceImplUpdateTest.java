package com.project.bankproject.unit.service.ProfileServiceImpl;

import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.entity.User;
import com.project.bankproject.repository.ProfileRepository;
import com.project.bankproject.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

/**
 * class UpdateTest
 *
 * @author damir.iusupov
 * @since 2022-04-15
 */
@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplUpdateTest {
    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Test
    public void testPositive() {
        String login = "mockLogin";
        User user = new User().setLogin(login);
        String firstName = "mockFirstName";
        String lastName = "mockLastName";
        Profile profile = new Profile().setFirstName(firstName).setLastName(lastName).setBalance(123D);
        profile.setUser(user);
        when(profileRepository.save(profile)).thenReturn(profile);
        Profile result = profileService.update(profile);
        assertEquals(123D, result.getBalance());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertSame(user, result.getUser());
    }
}
