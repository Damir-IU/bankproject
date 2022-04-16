package com.project.bankproject.unit.service.ProfileServiceImpl;

import com.project.bankproject.domain.entity.Profile;
import com.project.bankproject.domain.exception.EntityNotFoundException;
import com.project.bankproject.repository.ProfileRepository;
import com.project.bankproject.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * class FindByIdTest
 *
 * @author damir.iusupov
 * @since 2022-04-13
 */
@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplFindByIdTest {
    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Test
    public void testProfileNotFound() {
        UUID id = UUID.randomUUID();
        when(profileRepository.findById(id)).thenReturn(Optional.empty());
        try {
            profileService.findById(id);
        } catch (EntityNotFoundException exception) {
            assertEquals("Profile with id: " + id + " NOT FOUND", exception.getMessage());
        }
    }

    @Test
    public void testProfileFound() {
        UUID id = UUID.randomUUID();
        String firstName = "mockFirstName";
        String lastName = "mockLastName";
        Profile profile = new Profile().setFirstName(firstName).setLastName(lastName);
        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
        Profile result = profileService.findById(id);
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
    }
}
