package com.socialnetwork.service.impl;

import com.socialnetwork.model.dto.PagedModel;
import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.comments.CommentDTO;
import com.socialnetwork.model.entity.Comment;
import com.socialnetwork.model.entity.Post;
import com.socialnetwork.model.entity.User;
import com.socialnetwork.repository.CommentRepository;
import com.socialnetwork.repository.PostRepository;
import com.socialnetwork.repository.UserRepository;
import com.socialnetwork.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseModel<CommentDTO> getById(UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment " + id.toString()));
        return ResponseModel.ok(mapper.map(comment, CommentDTO.class), "Get comment success");
    }

    @Override
    public ResponseModel<PagedModel> getAll(int pageSize, int pageIndex, Optional<UUID> postId) {
        Pageable page = PageRequest.of(pageIndex - 1, pageSize);

        Page<Comment> commentPaged = postId.isPresent() ?
                commentRepository.findByPostId(postId.get(), page) :
                commentRepository.findAll(page);

        List<CommentDTO> commentDto = commentPaged.getContent().stream().map(comment -> mapper.map(comment, CommentDTO.class)).toList();

        return ResponseModel.ok(
                new PagedModel((int) commentPaged.getTotalElements(), commentPaged.getTotalPages(), commentDto),
                "Get comment success"
        );
    }

    @Override
    public ResponseModel<CommentDTO> createComment(UUID userId, CommentDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User " + userId.toString()));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new EntityNotFoundException("Post " + request.getPostId().toString()));

        Comment newComment = commentRepository.save(
                new Comment(request.getContent(), user, post)
        );

        return ResponseModel.created(mapper.map(newComment, CommentDTO.class));
    }

    @Override
    public ResponseModel<CommentDTO> updateComment(UUID userId, UUID commentId, CommentDTO request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment " + commentId.toString()));

        if (!comment.getUser().getId().equals(userId)) {
            return ResponseModel.badRequest("Not owner");
        }

        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return ResponseModel.ok(mapper.map(comment, CommentDTO.class), "Update success");
    }

    @Override
    public ResponseModel<?> deleteComment(UUID userId, UUID commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment " + commentId.toString()));

        if (!comment.getUser().getId().equals(userId)) {
            return ResponseModel.badRequest("Not owner");
        }

        commentRepository.delete(comment);

        return ResponseModel.ok(null, "Delete success");
    }
}
