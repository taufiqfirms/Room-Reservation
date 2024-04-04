package com.kelompokdua.booking.security;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kelompokdua.booking.entity.UserCredential;
import com.kelompokdua.booking.model.JwtClaim;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
    @Value("${app.enigma-coop.jwt-secret}")
    private String secretKey;
    @Value("${app.enigma-coop.jwt-expiration}")
    private Long expirationInSecond;
    @Value("${app.enigma-coop.jwt-app-name}")
    private String appName;

     public String generateToken(UserCredential userCredential){
        try{
            List<String> roles = userCredential
            .getRoles()
            .stream()
            .map(role -> role.getRole().name()).toList();

            return JWT
            .create()
            .withIssuer(appName)
            .withSubject(userCredential.getId())
            .withExpiresAt(Instant.now().plusSeconds(expirationInSecond))
            .withClaim("roles", roles)
            .sign(Algorithm.HMAC512(secretKey));

        }catch(JWTCreationException e){
            log.error("Invalid while creating JWT token : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean verifyJwtToken(String token){
        try {
            DecodedJWT decodedJWT = getDecodedJWT(token);
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException e) {
           log.error("Invalid verification JWT token : {}", e.getMessage());
           return false;
        }
    }

    private DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }

    public JwtClaim getUserInfoByToken(String token){
        try {
            DecodedJWT decodedJWT = getDecodedJWT(token);
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            return JwtClaim.builder()
            .userId(decodedJWT.getSubject())
            .roles(roles)
            .build();
        } catch (JWTVerificationException e) {
            log.error("Invalid verification user info JWT : {}", e.getMessage());
           return null;
        }
    }
}
