package com.npcweb.controller;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

@SuppressWarnings("deprecation")
public class JWTProvider {
	private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generateToken(long userno) {
		return Jwts.builder()
				.setSubject(Long.toString(userno))
	            .signWith(secretKey)
	            .compact();
	}
	
	public Long getUsernoFromToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
			return Long.parseLong(claims.getSubject());
	    } catch (SignatureException e) {
	    	// 서명 검증 실패
	    	System.out.println("서명 검증 실패");
	        return null;
	    } catch (Exception e) {
	        // 기타 예외 처리
	        return null;
	    }
	}
}