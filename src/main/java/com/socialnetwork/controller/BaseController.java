package com.socialnetwork.controller;

import com.socialnetwork.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public class BaseController {
    protected UUID getUserId() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return ((User) userDetails).getId();
        } else {
            return null;
        }
    }
}
