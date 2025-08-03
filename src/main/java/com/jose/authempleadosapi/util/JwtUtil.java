package com.jose.authempleadosapi.util;

import com.jose.authempleadosapi.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expiration;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generateToken(String username, List<Role> roles) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration);

    List<String> roleNames = roles.stream().map(Role::name).toList();

    return Jwts.builder()
        .setSubject(username)
        .claim("roles", roleNames)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsername(String token) {
    return parseClaims(token).getSubject();
  }

  public List<String> extractRoles(String token) {
    return parseClaims(token).get("roles", List.class);
  }

  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  private Claims parseClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}
