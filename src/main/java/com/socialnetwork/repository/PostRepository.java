package com.socialnetwork.repository;

import com.socialnetwork.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
