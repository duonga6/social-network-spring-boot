package com.socialnetwork.model.dto.posts;

import com.socialnetwork.model.entity.PostImage;

import java.util.List;
import java.util.UUID;

public class PostDTO {
    private UUID id;
    private String content;
    private List<PostImage> postImages;
}
