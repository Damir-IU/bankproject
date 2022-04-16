package com.project.bankproject.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * class ProfileCreateDto for Profile's Dto layer.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ProfileCreateDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
}
