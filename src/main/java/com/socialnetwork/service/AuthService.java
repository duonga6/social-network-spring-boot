package com.socialnetwork.service;

import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.auth.Token;
import com.socialnetwork.model.dto.auth.Login;
import com.socialnetwork.model.dto.auth.Register;

public interface AuthService {
    ResponseModel<Token> login(Login request);
    ResponseModel<Token> register(Register request);
}
