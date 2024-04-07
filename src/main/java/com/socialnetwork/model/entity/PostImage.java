package com.socialnetwork.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PostImages")
public class PostImage extends BaseEntity {
    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;
}
