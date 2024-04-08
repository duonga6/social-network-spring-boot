package com.socialnetwork.model.dto.postImages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostImageDTO {
    private UUID id;
    private String url;
    private Date createdAt;
}
