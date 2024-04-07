package com.socialnetwork.service;

import com.socialnetwork.model.dto.PagedModel;
import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.users.UpdateUserDTO;
import com.socialnetwork.model.dto.users.UserDTO;

import java.util.UUID;

public interface UserService {
    ResponseModel<PagedModel> getAllUser(int pageSize, int pageIndex);
    ResponseModel<UserDTO> getById(UUID id);
    ResponseModel<UserDTO> update(UUID requestUserId, UUID id, UpdateUserDTO request);
    ResponseModel<?> delete(UUID requestUserId, UUID id);
}
