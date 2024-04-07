package com.socialnetwork.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private Date createdAt = new Date();

    @Column(nullable = false)
    private Date updatedAt = new Date();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}
