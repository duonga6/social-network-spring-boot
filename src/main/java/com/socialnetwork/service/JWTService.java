package com.socialnetwork.service;

import com.socialnetwork.model.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JWTService {
    String generateToken(User user);
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolver);
    boolean isValidToken(String token, UserDetails user);
    boolean isTokenExpired(String token);
}
