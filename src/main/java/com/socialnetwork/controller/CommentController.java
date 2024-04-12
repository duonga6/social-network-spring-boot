package com.socialnetwork.controller;

import com.socialnetwork.model.dto.comments.CommentDTO;
import com.socialnetwork.service.CommentService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("comments")
public class CommentController extends BaseController {

    private  final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Positive @RequestParam int pageSize, @Positive @RequestParam int pageIndex, @RequestParam UUID postId) {
        var response = commentService.getAll(pageSize, pageIndex, Optional.of(postId));
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var response = commentService.getById(id);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CommentDTO request) {
        var response = commentService.createComment(this.getUserId(), request);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CommentDTO request, @PathVariable UUID id) {
        var response = commentService.updateComment(this.getUserId(), id, request);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var response = commentService.deleteComment(this.getUserId(), id);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }
}
