package com.socialnetwork.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Posts")
public class Post extends BaseEntity {
    private String content;
    private String images;

    @OneToMany(mappedBy = "post")
    private Set<PostLike> postLiked;

    @OneToMany(mappedBy = "post")
    private Set<PostImage> postImages;
}
