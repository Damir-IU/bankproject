package com.project.bankproject.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class UserUpdateDto for User's Dto layer.
 *
 * @author damir.iusupov
 * @since 2022-04-01
 */
@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
    private String login;
    private String email;
    private String password;
}
