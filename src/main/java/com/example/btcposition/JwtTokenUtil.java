package com.example.btcposition;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {


  private final String secret;

  @Autowired
  public JwtTokenUtil( @Value("${jwt.secret}") String secret) {
    this.secret = secret;
  }

  public String generateToken(String username, boolean voted) {


    Claims claims = Jwts.claims().setSubject(username);
    claims.put("voted", voted);

    Instant now = Instant.now();
    Instant instant = now.plusSeconds(EXPIRATION_TIME);

    System.out.println("instant = " + instant);
    System.out.println("now = " + now);


//    Date now = new Date();
//    Date expiration = new Date(now.getTime() + 10000000L);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(instant))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();

  }

  public  String getUsernameFromToken(String token) {

    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7); // "Bearer " 접두사를 제거합니다.
    }

      return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }


  public  boolean isVoted(String token) {

    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7); // "Bearer " 접두사를 제거합니다.
    }

    return (boolean) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()
        .get("voted");
  }


  private static final long EXPIRATION_TIME = calculateExpirationTime();

  private static long calculateExpirationTime() {
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    Instant instant = tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant();
    return instant.getEpochSecond();

  }


}
