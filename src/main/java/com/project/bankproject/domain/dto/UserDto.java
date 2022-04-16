package com.project.bankproject.domain.dto;

import com.project.bankproject.domain.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

/**
 * class UserDto for User's Dto layer.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto {
    private UUID id;
    private String login;
    private String email;
    private Role role;
    private UUID profileId;
    private Set<UUID> bankAccountIds;
}
