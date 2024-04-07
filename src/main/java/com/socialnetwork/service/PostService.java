package com.socialnetwork.service;

import com.socialnetwork.model.dto.PagedModel;
import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.posts.PostDTO;
import com.socialnetwork.model.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    ResponseModel<PagedModel> getAll(int pageSize, int pageIndex);
    ResponseModel<PostDTO> getById(UUID postId);
    ResponseModel<PostDTO> createPost(UUID requestUserId, Post request);
    ResponseModel<PostDTO> updatePost(UUID requestUserId, UUID postId, Post request);
    ResponseModel<?> deletePost(UUID requestUserId, UUID postId);
}
