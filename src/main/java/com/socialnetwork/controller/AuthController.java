package com.socialnetwork.controller;

import com.socialnetwork.model.dto.auth.Login;
import com.socialnetwork.model.dto.auth.Register;
import com.socialnetwork.model.entity.User;
import com.socialnetwork.repository.UserRepository;
import com.socialnetwork.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login request) {
        var response = authService.login(request);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Register request) {
        var response = authService.register(request);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/test_admin")
    public ResponseEntity<?> testAdmin() {
        User user = userRepository.findById(this.getUserId()).orElseThrow();
        return ResponseEntity.ok(this.getUserId() + " Is admin: " + user.isAdmin());
    }

    @GetMapping("/test_user")
    public ResponseEntity<?> testUser() {
        return ResponseEntity.ok(this.getUserId());
    }
}
