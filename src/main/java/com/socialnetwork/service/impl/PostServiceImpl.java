package com.socialnetwork.service.impl;

import com.socialnetwork.model.dto.PagedModel;
import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.posts.PostDTO;
import com.socialnetwork.model.entity.Post;
import com.socialnetwork.model.entity.PostImage;
import com.socialnetwork.model.entity.User;
import com.socialnetwork.repository.PostRepository;
import com.socialnetwork.repository.UserRepository;
import com.socialnetwork.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseModel<PagedModel> getAll(int pageSize, int pageIndex) {
        Pageable page = PageRequest.of(pageIndex - 1, pageSize);
        Page<Post> postPaged = postRepository.findAll(page);

        List<PostDTO> postDto = postPaged.getContent().stream().map(post -> mapper.map(post, PostDTO.class)).toList();

        return ResponseModel.ok(
                new PagedModel((int) postPaged.getTotalElements(), postPaged.getTotalPages(), postDto),
                "Get post success"
        );
    }

    @Override
    public ResponseModel<PostDTO> getById(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post"));

        return ResponseModel.ok(mapper.map(post, PostDTO.class), "Get post success");
    }

    @Override
    public ResponseModel<PostDTO> createPost(UUID requestUserId, Post request) {
        User user = userRepository.findById(requestUserId).orElseThrow(() -> new EntityNotFoundException("User"));

        if (request.getContent().isEmpty() && request.getPostImages().isEmpty()) {
            return ResponseModel.badRequest("Post must have content or images");
        }

        Post newPost = postRepository.save(
                new Post(request.getContent(), user, request.getPostImages())
        );

        return ResponseModel.created(mapper.map(newPost, PostDTO.class));
    }

    @Override
    public ResponseModel<PostDTO> updatePost(UUID requestUserId, UUID postId, Post request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post"));

        if (!post.getUser().getId().equals(requestUserId)) {
            return ResponseModel.badRequest("Access denied");
        }

        List<PostImage> currentImages = post.getPostImages();
        List<PostImage> updatedImages = request.getPostImages();

        currentImages.removeIf(item -> !updatedImages.contains(item));

        for (PostImage updatedImage: updatedImages) {
            int index = currentImages.indexOf(updatedImage);
            if (index != - 1) {
                currentImages.set(index, updatedImage);
            } else {
                currentImages.add(updatedImage);
            }
        }

        post.setPostImages(currentImages);
        post.setContent(request.getContent());

        Post updatedPost = postRepository.save(post);

        return ResponseModel.ok(mapper.map(updatedPost, PostDTO.class), "Update post success");
    }

    @Override
    public ResponseModel<?> deletePost(UUID requestUserId, UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post"));
        User user = userRepository.findById(requestUserId).orElseThrow(() -> new EntityNotFoundException("User"));

        if (!user.getId().equals(post.getUser().getId()) && !user.isAdmin()) {
            return ResponseModel.badRequest("Access denied");
        }

        postRepository.delete(post);

        return ResponseModel.noContent();
    }
}
