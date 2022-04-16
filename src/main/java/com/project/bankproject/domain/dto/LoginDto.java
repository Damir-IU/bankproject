package com.project.bankproject.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * class LoginDto for User's Dto layer.
 *
 * @author damir.iusupov
 * @since 2022-04-04
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
