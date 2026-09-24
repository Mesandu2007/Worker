package com.example.Worker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long expirationTime=1000*60*60*24;


    private SecretKey getSigningKey(){

         byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);


    }

    public String generateToken(Long id, String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("id", id)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token){

        Number id = extractAllClaims(token).get("id", Number.class);

        return id != null ? id.longValue() : null;


    }

    public String extractRole(String token){

        return extractAllClaims(token).get("role", String.class);

    }

    public boolean isTokenValid(String token){


        try{

            extractAllClaims(token);
            return !isTokenExpired(token);

        } catch(Exception e){

            return false;
        }
    }

    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }




}
