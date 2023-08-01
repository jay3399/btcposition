package com.example.btcposition;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Base64Utils;

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

  }

}
