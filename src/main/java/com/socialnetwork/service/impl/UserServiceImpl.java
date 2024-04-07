package com.socialnetwork.service.impl;

import com.socialnetwork.model.dto.PagedModel;
import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.users.UpdateUserDTO;
import com.socialnetwork.model.dto.users.UserDTO;
import com.socialnetwork.model.entity.User;
import com.socialnetwork.repository.UserRepository;
import com.socialnetwork.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseModel<PagedModel> getAllUser(int pageSize, int pageIndex) {
        Pageable page = PageRequest.of(pageIndex - 1, pageSize);
        Page<User> userPaged = userRepository.findAll(page);

        List<UserDTO> userDto = userPaged.getContent().stream().map(user -> mapper.map(user, UserDTO.class)).toList();

        return ResponseModel.ok(
                new PagedModel((int) userPaged.getTotalElements(), userPaged.getTotalPages(), userDto),
                "Get user success"
        );
    }

    @Override
    public ResponseModel<UserDTO> getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
        return ResponseModel.ok(mapper.map(user, UserDTO.class), "Get user success");
    }

    @Override
    public ResponseModel<UserDTO> update(UUID requestUserId, UUID id, UpdateUserDTO request) {
        if (!requestUserId.equals(id)) {
            return ResponseModel.badRequest("Access denied");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));

        mapper.map(request, user);
        User updatedUser = userRepository.save(user);

        return ResponseModel.ok(mapper.map(updatedUser, UserDTO.class), "Update user success");
    }

    @Override
    public ResponseModel<?> delete(UUID requestUserId, UUID id) {
        if (!requestUserId.equals(id)) {
            return ResponseModel.badRequest("Access denied");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));

        userRepository.delete(user);
        return ResponseModel.noContent();
    }
}
