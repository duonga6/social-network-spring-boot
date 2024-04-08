package com.socialnetwork.model.dto.posts;

import com.socialnetwork.model.dto.postImages.PostImageDTO;
import com.socialnetwork.model.dto.users.UserDTO;
import com.socialnetwork.model.entity.PostImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private UUID id;
    private String content;
    private List<PostImageDTO> postImages;
    private UserDTO user;
    private Date createdAt;
}
