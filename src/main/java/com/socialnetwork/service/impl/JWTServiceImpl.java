package com.socialnetwork.service.impl;

import com.socialnetwork.model.entity.User;
import com.socialnetwork.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    private final String SECRET_KEY = "1afe090332176b3073f1950ea3fbf4e2bc49bf75ee1a71165712e2b2cfc599be";

    @Override
    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(generateSignKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    @Override
    public boolean isValidToken(String token, UserDetails user) {
        String userName = extractUsername(token);
        return (userName.equals(user.getUsername()))
                && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date expiredTime = extractClaim(token, Claims::getExpiration);
        return expiredTime.before(new Date());
    }

    private SecretKey generateSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
