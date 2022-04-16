package com.project.bankproject.repository;

import com.project.bankproject.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * interface ProfileRepository extends {@link JpaRepository} for class {@link Profile}.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
public interface ProfileRepository extends JpaRepository<Profile, UUID> {
}
