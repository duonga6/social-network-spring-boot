package com.socialnetwork.controller;

import com.socialnetwork.model.entity.Post;
import com.socialnetwork.service.PostService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController extends BaseController{
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Positive @RequestParam int pageSize, @Positive @RequestParam int pageIndex) {
        var response = postService.getAll(pageSize, pageIndex);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var response = postService.getById(id);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Post request) {
        var response = postService.createPost(this.getUserId(), request);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Post request, @PathVariable UUID id) {
        var response = postService.updatePost(this.getUserId(), id, request);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var response = postService.deletePost(this.getUserId(), id);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }
}
