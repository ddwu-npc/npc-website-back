package com.npcweb.security;

import java.security.Key;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTProvider {
    @Value("${jwt.secretKey}")
    private String secretKeyPlain;
	private SecretKey secretKey;
	
    private SecretKey encodeSecretKey() {
    	System.out.println("sk bean" + secretKeyPlain);
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

		// 시크릿 키를 반환하는 method
    public SecretKey getSecretKey() {
        if (secretKey == null) secretKey = encodeSecretKey();

        return secretKey;
    }
    
	public String generateToken(long userno) {
		return Jwts.builder()
				.setSubject(Long.toString(userno))
	            .signWith(getSecretKey())
	            .compact();
	}
	
	public Long getUsernoFromToken(String token) {
		System.out.println(token);
		Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
			return Long.parseLong(claims.getSubject());
	}
}