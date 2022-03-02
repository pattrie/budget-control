package com.github.pattrie.budgetcontrol.usecases;

import com.github.pattrie.budgetcontrol.domains.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  @Value("${security.jwt.expiration}")
  private String expiration;

  @Value("${security.jwt.secret}")
  private String secret;

  public String generateToken(Authentication authentication) {
    final User logged = (User) authentication.getPrincipal();
    Date today = new Date();
    Date expirationDate = new Date(today.getTime() + Long.parseLong(this.expiration));

    return Jwts.builder()
        .setIssuer("API - Budget Control")
        .setSubject(logged.getId())
        .setIssuedAt(today)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS256, this.secret)
        .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getUserId(String token) {
    final Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }
}
