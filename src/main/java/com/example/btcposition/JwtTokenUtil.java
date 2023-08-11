package com.example.btcposition;

import com.example.btcposition.exception.JWTException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {


    private final String secret;

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(String username) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("voted", false);

        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(instant);

        // 토큰 만료 시간 계산 (다음 날 자정으로)
        LocalDate nextDay = now.toLocalDate().plusDays(1);
        LocalDateTime midnight = LocalDateTime.of(nextDay, LocalTime.MIDNIGHT);
        Instant expirationInstant = midnight.atZone(ZoneId.systemDefault()).toInstant();
        Date from1 = Date.from(expirationInstant);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(expirationInstant))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public String getUsernameFromToken(HttpServletRequest request) {

        String token = extractToken(request);

        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException e) {
            throw new JWTException("토큰 파싱중 오류가 발생했습니다");
        }
    }


    // 기존의 토큰정보를 꺼내와 , claims 의 voted 부분을 true로 변경 .
    public String getUpdatedToken(HttpServletRequest request) {
        String token = extractToken(request);

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        claims.put("voted", true);

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isVoted(HttpServletRequest request) {

        String token = extractToken(request);

        try {
            return (boolean) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()
                    .get("voted");
        } catch (JwtException e) {
            throw new JWTException("토큰 검증 중 오류가 발생했습니다");
        }
    }

    public Boolean isTokenExpired(HttpServletRequest request) {

        String token = extractToken(request);

        try {
            return getExpirationDateFromToken(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);

    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = gettAllClaimsFromToken(token);
        return claimsTFunction.apply(claims);
    }

    private Claims gettAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    private static String extractToken(HttpServletRequest request) {
        String token = getAuthorization(request);
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7); // "Bearer " 접두사를 제거합니다.
        }
        return null;
    }

    private static String getAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization");
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
