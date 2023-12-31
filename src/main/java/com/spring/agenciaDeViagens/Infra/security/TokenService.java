package com.spring.agenciaDeViagens.Infra.security;

import com.auth0.jwt.JWT;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.spring.agenciaDeViagens.model.Customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TokenService {

    @Value("${config.token.secret}")
    private String secretKey;
    public String tokenGenerate(Customer customer){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            return JWT.create()
                    .withSubject(customer.getEmail())
                    .withClaim("id", customer.getId())
                    .withClaim("name", customer.getName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                    .withIssuer("Ada Tech")
                    .sign(algorithm);
        }catch (JWTCreationException exception) {
            throw new RuntimeException("erro to generate token", exception);
        }
    }

    public String getSubject(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Ada Tech")
                    .build();

            return verifier.verify(token).getSubject();
        } catch (JWTVerificationException exception){
        	System.out.println(new Date(System.currentTimeMillis() + 3600000));
        	System.err.println("Token verification failed: " + exception.getMessage());
            throw new RuntimeException("invalid token");
        }
    }
}
