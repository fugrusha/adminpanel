package com.golovko.adminpanel.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AppUserCreateDTO {

    @NotNull
    private String username;

    @NotNull
    private String chatId;

    @NotNull
    @Pattern(regexp = "^(?=\\S+$).{8,}$", message = "Password should contain at least 8 characters without spaces")
    private String password;

    @NotNull
    @Pattern(regexp = "^(?=\\S+$).{8,}$", message = "Password should contain at least 8 characters without spaces")
    private String passwordConfirmation;
}
