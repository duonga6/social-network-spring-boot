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
@Table(name = "Roles")
public class Role extends BaseEntity {
    @Column(nullable = false)
    @Enumerated
    private RoleEnum roleName;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> listUsers;

    public Role(RoleEnum roleName) {
        this.roleName = roleName;
    }
}
