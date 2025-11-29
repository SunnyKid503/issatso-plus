package com.university.forum.usermanagement.Utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.token.expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(UUID userId, String username, Set<String> roles) {
        Set<String> prefixedRoles = roles.stream()
                .map(role -> "ROLE_" + role)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId.toString()) // Convert UUID to String
                .claim("roles", prefixedRoles)
                .claim("tokenType", "ACCESS")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UUID userId, String username, Set<String> roles) {
        Set<String> prefixedRoles = roles.stream()
                .map(role -> "ROLE_" + role)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId.toString())
                .claim("roles", prefixedRoles)
                .claim("tokenType", "REFRESH")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        if (token == null || !token.contains(".")) {  // Ensure the token is not null and has periods
            return false; // Invalid token format
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);  // This will throw an exception if the token is invalid
            return true; // Token is valid
        } catch (MalformedJwtException e) {
            // Handle malformed token (e.g., log the error or notify the user)
            return false;  // Token format is invalid
        } catch (JwtException e) {
            // Handle other JWT-related exceptions
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        if (token == null || !token.contains(".")) { // Ensure token has the correct format
            throw new IllegalArgumentException("Invalid token format");
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();  // Extract username (subject) from token
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid token or unable to parse", e);
        }
    }

    public UUID getUserIdFromToken(String token) {
        if (token == null || !token.contains(".")) { // Ensure token has the correct format
            throw new IllegalArgumentException("Invalid token format");
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userIdString = claims.get("userId", String.class);

            if (userIdString == null || userIdString.isEmpty()) {
                throw new IllegalArgumentException("userId claim is missing or empty in token");
            }
            return UUID.fromString(userIdString);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid token or unable to parse", e);
        }
    }

    public Set<String> getRolesFromToken(String token) {
        if (token == null || !token.contains(".")) { // Ensure token has the correct format
            throw new IllegalArgumentException("Invalid token format");
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            List<String> rolesList = claims.get("roles", List.class);  // Get roles from token claims
            return new HashSet<>(rolesList);  // Return roles as a Set
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid token or unable to parse", e);
        }
    }

    public String getTokenTypeFromToken(String token) {
        if (token == null || !token.contains(".")) {
            throw new IllegalArgumentException("Invalid token format");
        }

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("tokenType", String.class);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid token or unable to parse", e);
        }
    }

    private Key getSigningKey() {
        return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

}