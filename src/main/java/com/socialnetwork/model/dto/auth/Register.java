package com.socialnetwork.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Register {
    @NotNull(message = "First name is not empty")
    private String firstName;

    @NotNull(message = "Last name is not empty")
    private String lastName;

    @NotNull(message = "Email is not empty")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Email is not empty")
    private String password;

    private String avatarUrl;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String job;
}
