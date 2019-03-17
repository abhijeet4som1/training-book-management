package com.training.bookmanagement.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDto {

    @NotBlank(message = "User name is mandatory.")
    private String userName;

    @NotBlank(message = "Password is mandatory.")
    private String password;
}
