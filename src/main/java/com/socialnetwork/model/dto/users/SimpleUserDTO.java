package com.socialnetwork.model.dto.users;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SimpleUserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String avatarUrl;
}
