package com.his.security;

import io.jsonwebtoken.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private String SECRET_KEY = "secret";
    public Set<String> jwtDenyList = new HashSet<>();


    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date now = new Date();

            return userDetails.getUsername().equals(getUsernameFromToken(token))
                    && !expirationDate.before(now);
        }  catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(new HashMap<String, Object>())
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        if (isTokenExpired(token)) {
            return null;
        }
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void logout(String jwtToken) {
        jwtDenyList.add(jwtToken);
    }

    public boolean isTokenDenied(String jwtToken) {
        return jwtDenyList.contains(jwtToken);
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void removeExpiredTokens() {
        jwtDenyList.removeIf(this::isTokenExpired);
    }

    private boolean isTokenExpired(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }
}

