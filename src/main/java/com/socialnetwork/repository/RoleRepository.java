package com.socialnetwork.repository;

import com.socialnetwork.model.entity.Role;
import com.socialnetwork.model.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    public Optional<Role> findByRoleName(RoleEnum roleName);
}
