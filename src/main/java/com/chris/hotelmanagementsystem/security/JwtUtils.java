package com.chris.hotelmanagementsystem.security;

import com.chris.hotelmanagementsystem.entity.error.CxException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

  @Value("${chris.entity.jwt-secret}")
  private String jwtSecret;

  @Value("${chris.entity.jwt-expirationDays}")
  private int jwtExpirationDays;

  public String generateJwtToken(Authentication auth) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();

    return Jwts.builder()
            .subject(userPrincipal.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + (long) jwtExpirationDays * 24 * 60 * 60))
            .signWith(key())
            .compact();
  }


  public boolean validateJwtToken(String token) {
    if (token == null)
      return false;

    try {
      Jwts.parser()
              .verifyWith(key())
              .build()
              .parse(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw CxException.hardcoded("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
      throw CxException.hardcoded("JWT token is expired");
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
      throw CxException.hardcoded("JWT token is unsupported");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw CxException.hardcoded("JWT claims string is empty");
    }
  }

  private SecretKey key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser()
            .decryptWith(key())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
  }
}
