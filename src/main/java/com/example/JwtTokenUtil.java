package com.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {


  @Value("${jwt.secret}")
  private static String secret;


  public static String generateToken(String username, boolean voted) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("voted", voted);

    Instant now = Instant.now();
    Instant instant = now.plusSeconds(EXPIRATION_TIME);


//    Date now = new Date();
//    Date expiration = new Date(now.getTime() + 10000000L);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(instant))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();

  }

  public static String getUsernameFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }


  public static boolean isVoted(String token) {
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
