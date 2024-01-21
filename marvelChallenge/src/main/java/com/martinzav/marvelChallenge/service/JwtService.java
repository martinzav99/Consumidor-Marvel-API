package com.martinzav.marvelChallenge.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        
        Date issuedAt = new Date(System.currentTimeMillis());
        
        Date expiredAt = new Date(issuedAt.getTime() + (EXPIRATION_IN_MINUTES *60 * 1000) );

        return Jwts.builder().setClaims(extraClaims)
                             .setSubject(user.getUsername())
                             .setIssuedAt(issuedAt)
                             .setExpiration(expiredAt)
                             .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                             .signWith(generateKey(),SignatureAlgorithm.HS256)
                             .compact();
    }

    private Key generateKey() {
        byte[] secretKey =Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretKey);
    }

    public String extractSubject(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt){
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody();
    }
    
}
