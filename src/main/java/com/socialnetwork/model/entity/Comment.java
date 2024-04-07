package com.socialnetwork.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Comments")
public class Comment extends BaseEntity {
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
