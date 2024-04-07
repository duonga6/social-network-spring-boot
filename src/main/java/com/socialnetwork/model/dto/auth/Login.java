package com.socialnetwork.model.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    @NotNull(message = "Email is not empty")
    private String email;
    @NotNull(message = "Email is not empty")
    private String password;
}
