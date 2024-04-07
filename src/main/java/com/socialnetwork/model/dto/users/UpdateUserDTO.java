package com.socialnetwork.model.dto.users;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateUserDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String avatarUrl;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String job;
}
