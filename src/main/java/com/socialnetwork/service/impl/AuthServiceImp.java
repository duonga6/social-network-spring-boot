package com.socialnetwork.service.impl;

import com.socialnetwork.model.dto.ResponseModel;
import com.socialnetwork.model.dto.Token;
import com.socialnetwork.model.dto.auth.Login;
import com.socialnetwork.model.dto.auth.Register;
import com.socialnetwork.model.entity.Role;
import com.socialnetwork.model.entity.RoleEnum;
import com.socialnetwork.model.entity.User;
import com.socialnetwork.model.entity.UserRole;
import com.socialnetwork.repository.RoleRepository;
import com.socialnetwork.repository.UserRepository;
import com.socialnetwork.repository.UserRoleRepository;
import com.socialnetwork.service.AuthService;
import com.socialnetwork.service.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImp(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, JWTService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseModel<Token> login(Login request) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException(""));
            String token = jwtService.generateToken(user);

            return ResponseModel.ok(
                    new Token(token),
                    "Login success");

        } catch (BadCredentialsException ex) {
            return ResponseModel.badRequest("Invalid email or password");
        }
    }

    @Override
    public ResponseModel<Token> register(Register request) {
        try {
            Optional<User> user = userRepository.findByEmail(request.getEmail());
            if (user.isPresent()) {
                return ResponseModel.badRequest("Email is taken");
            }

            User newUser = userRepository.save(new User(request.getFirstName(), request.getLastName(), passwordEncoder.encode(request.getPassword()), request.getEmail(), request.getAvatarUrl(), request.getDateOfBirth(), request.getAddress(), request.getJob(), request.getPhoneNumber()));
            Role roleUser = roleRepository.findByRoleName(RoleEnum.ROLE_USER).orElseThrow(() -> new NoSuchElementException("ROLE_USER not found"));
            UserRole roleForUser = new UserRole(newUser, roleUser);

            userRoleRepository.save(roleForUser);
            String token = jwtService.generateToken(newUser);

            return ResponseModel.ok(
                    new Token(token),
                    "Register success"
            );
        } catch (Exception ex) {
            return ResponseModel.internalError(ex.getMessage());
        }

    }
}
