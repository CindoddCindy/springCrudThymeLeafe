package com.buah.cindodcindy.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
  @Value("${jwt.secret}") private String secret;
  @Value("${jwt.expiration}") private long lifetime;
  public String generateToken(String u) {
    return Jwts.builder()
        .setSubject(u)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+lifetime))
        .signWith(SignatureAlgorithm.HS256, secret).compact();
  }

  public String extractUsername(String token) {
    return Jwts.parser().setSigningKey(secret)
        .parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (Exception e) { return false; }
  }
}
