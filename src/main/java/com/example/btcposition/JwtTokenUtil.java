package com.example.btcposition;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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


    LocalDateTime now = LocalDateTime.now();
    Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
    System.out.println("instant = " + instant);
    Date from = Date.from(instant);
    System.out.println("from = " + from);

    // 토큰 만료 시간 계산 (다음 날 자정으로)
    LocalDate nextDay = now.toLocalDate().plusDays(1);
    LocalDateTime midnight = LocalDateTime.of(nextDay, LocalTime.MIDNIGHT);
    Instant expirationInstant = midnight.atZone(ZoneId.systemDefault()).toInstant();
    Date from1 = Date.from(expirationInstant);
    System.out.println("expirationInstant = " + expirationInstant);

    System.out.println("from1 = " + from1);



//    Date now = new Date();
//    Date expiration = new Date(now.getTime() + 10000000L);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(Date.from(instant))
        .setExpiration(Date.from(expirationInstant))
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
    ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
    LocalDateTime now = LocalDateTime.now(koreaZoneId);

    LocalDateTime localDateTime = now.toLocalDate().atStartOfDay();

    LocalDateTime with = localDateTime.with(LocalDateTime.MAX);

    Duration between = Duration.between(now, with);
    return between.getSeconds();

  }


}
