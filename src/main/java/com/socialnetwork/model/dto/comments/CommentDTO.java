package com.socialnetwork.model.dto.comments;

import com.socialnetwork.model.dto.users.SimpleUserDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentDTO {
    private UUID id;
    @NotNull
    private String content;
    @NotNull
    private UUID postId;
    private SimpleUserDTO user;
}
