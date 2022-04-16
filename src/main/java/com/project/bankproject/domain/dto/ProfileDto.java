package com.project.bankproject.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * class ProfileDto for Profile's Dto layer.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ProfileDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private double balance;
    private UUID userId;
}
