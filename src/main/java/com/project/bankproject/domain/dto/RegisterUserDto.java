package com.project.bankproject.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * class RegisterUserDto for User's Dto layer.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter
@NoArgsConstructor
public class RegisterUserDto {
    @NotEmpty
    private String login;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
