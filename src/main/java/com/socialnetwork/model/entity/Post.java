package com.socialnetwork.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Posts")
public class Post extends BaseEntity {
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private Set<Comment> postComments;

    @OneToMany(mappedBy = "post")
    private Set<PostLike> postLiked;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages;

    public Post(UUID id, String content, User user, List<PostImage> postImages) {
        this.setId(id);
        this.content = content;
        this.user = user;
        this.postImages = postImages;
    }

    public Post(String content, User user, List<PostImage> postImages) {
        this.content = content;
        this.user = user;
        this.postImages = postImages;
    }

    public Post(User user, List<PostImage> postImages) {
        this.user = user;
        this.postImages = postImages;
    }

    public Post(UUID id, String content, User user) {
        this.setId(id);
        this.content = content;
        this.user = user;
    }
}
