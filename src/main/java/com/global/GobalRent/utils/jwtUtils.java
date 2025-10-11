package com.global.GobalRent.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class jwtUtils {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private String expiration;


    private Key getSignatureKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //generar token
    public String generateToken(String email){

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()
                +Long.parseLong(expiration)))
            .signWith(getSignatureKey(),SignatureAlgorithm.HS256)
            .compact();
    }

    //verificar el token
    public boolean  validateToken(String token){

        try{

            Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token);

            return true;
        
        }
        catch(JwtException | IllegalArgumentException e){

            return false;
        }

    }

    //obtener el subject
    public String getSubject(String token){

        return Jwts.parserBuilder()
            .setSigningKey(getSignatureKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}
