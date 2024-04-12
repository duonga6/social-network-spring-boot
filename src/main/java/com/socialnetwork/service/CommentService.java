package com.socialnetwork.service;

import com.socialnetwork.model.dto.PagedModel;
import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.comments.CommentDTO;

import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    ResponseModel<CommentDTO> getById(UUID id);
    ResponseModel<PagedModel> getAll(int pageSize, int pageIndex, Optional<UUID> postId);
    ResponseModel<CommentDTO> createComment(UUID userId, CommentDTO request);
    ResponseModel<CommentDTO> updateComment(UUID userId, UUID commentId, CommentDTO request);
    ResponseModel<?> deleteComment(UUID userId, UUID commentId);
}
