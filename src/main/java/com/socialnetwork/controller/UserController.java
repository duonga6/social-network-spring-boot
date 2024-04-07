package com.socialnetwork.controller;

import com.socialnetwork.model.dto.users.UpdateUserDTO;
import com.socialnetwork.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Positive @RequestParam int pageSize,@Positive @RequestParam int pageIndex) {
        var response = userService.getAllUser(pageSize, pageIndex);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var response = userService.getById(id);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody UpdateUserDTO request) {
        var response = userService.update(this.getUserId() ,id, request);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var response = userService.delete(this.getUserId(), id);
        return ResponseEntity
                .status(response.getHttpStatus())
                .body(response);
    }
}
