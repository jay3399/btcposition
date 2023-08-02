package com.example.btcposition;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BtcpositionApplication {

  public static void main(String[] args) {
    SpringApplication.run(BtcpositionApplication.class, args);

//    byte[] randomBytes = new byte[32];
//    new SecureRandom().nextBytes(randomBytes);
//
//    String base64EncodedKey = Base64.getEncoder().encodeToString(randomBytes);
//
//    System.out.println("randomBytes = " + base64EncodedKey);
//
//    int keyLength = 32; // 예: 32 바이트
//
//    // 랜덤 바이트 배열을 생성합니다.
//    byte[] randomBytes = new byte[keyLength];
//    SecureRandom secureRandom = new SecureRandom();
//    secureRandom.nextBytes(randomBytes);
//
//    String s = Base64Utils.encodeToUrlSafeString(randomBytes);
//    // Base64 인코딩된 랜덤 문자열을 얻습니다.
//    System.out.println("Random Key: " + s);
//
    LocalDateTime now = LocalDateTime.now();
    Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
    Date issuedAt = Date.from(instant);
    System.out.println("issuedAt = " + issuedAt);

    // 토큰 만료 시간 계산 (다음 날 자정으로)
    LocalDate nextDay = now.toLocalDate().plusDays(1);
    LocalDateTime midnight = LocalDateTime.of(nextDay, LocalTime.MIDNIGHT);
    Instant expirationInstant = midnight.atZone(ZoneId.systemDefault()).toInstant();
    Date expirationDate = Date.from(expirationInstant);
    System.out.println("expirationDate = " + expirationDate);




  }

}
