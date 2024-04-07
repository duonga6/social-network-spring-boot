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
@Table(name = "Friends")
public class Friend extends BaseEntity {
    private String status;

    @ManyToOne
    @JoinColumn(name = "fromId", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "toId", nullable = false)
    private User toUser;
}
