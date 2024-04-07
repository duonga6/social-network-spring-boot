package com.socialnetwork.service;

import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.Token;
import com.socialnetwork.model.dto.auth.Login;
import com.socialnetwork.model.dto.auth.Register;
import com.socialnetwork.model.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseModel<Token> login(Login request);
    ResponseModel<Token> register(Register request);
}
