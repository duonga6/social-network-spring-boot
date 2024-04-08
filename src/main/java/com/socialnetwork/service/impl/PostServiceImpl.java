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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (StringUtils.isBlank(request.getContent()) && CollectionUtils.isEmpty(request.getPostImages())) {
            return ResponseModel.badRequest("Post must have content or images");
        }

        User user = userRepository.findById(requestUserId).orElseThrow(() -> new EntityNotFoundException("User"));


        Post newPost = new Post(request.getContent(), user, request.getPostImages());
        newPost.getPostImages().forEach(image -> {
            image.setPost(newPost);
        });

        Post postSaved = postRepository.save(newPost);

        return ResponseModel.created(mapper.map(postSaved, PostDTO.class));
    }

    @Override
    public ResponseModel<PostDTO> updatePost(UUID requestUserId, UUID postId, Post request) {
        if (StringUtils.isBlank(request.getContent()) && CollectionUtils.isEmpty(request.getPostImages())) {
            return ResponseModel.badRequest("Post must have content or images");
        }

        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post"));

        if (!post.getUser().getId().equals(requestUserId)) {
            return ResponseModel.badRequest("Access denied");
        }

        List<PostImage> currentImages = post.getPostImages();
        List<PostImage> updatedImages = request.getPostImages() != null ? request.getPostImages() : Collections.emptyList();

        List<UUID> updatedImagesId = updatedImages.stream().map(PostImage::getId).toList();
        currentImages.removeIf(item -> !updatedImagesId.contains(item.getId()));

        List<UUID> currentImagesId = currentImages.stream().map(PostImage::getId).toList();

        for (PostImage updatedImage: updatedImages) {
            int index = currentImagesId.indexOf(updatedImage.getId());
            if (index != - 1) {
                currentImages
                        .get(index)
                        .setUrl(updatedImage.getUrl());
            } else {
                updatedImage.setPost(post);
                currentImages.add(updatedImage);
            }
        }

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

        return ResponseModel.ok(null, "Delete post success");
    }
}
