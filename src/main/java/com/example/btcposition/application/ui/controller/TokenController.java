package com.example.btcposition.application.ui.controller;

import com.example.btcposition.application.ui.response.JwtResponse;
import com.example.btcposition.domain.vote.service.interfaces.HashRedisService;
import com.example.btcposition.infrastructure.util.JwtTokenUtil;
import com.example.btcposition.exception.JWTException;
import com.example.btcposition.domain.vote.service.HashRedisServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final HashRedisService redisService;

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(
            @RequestBody Map<String, List<Map<String, Object>>> body) throws Exception {

        String hash = getHash(body);

        redisService.isExist(hash);

        redisService.setValidate(hash);

        String username = generateRandomUsername();

        String token = jwtTokenUtil.generateToken(username);

        // 객체에 Getter 없을시 , not accept 406오류.

        return ResponseEntity.ok(new JwtResponse(token, username));

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(
            @RequestBody Map<String, List<Map<String, Object>>> body) {

        String hash = getHash(body);

        redisService.setValidate(hash);

        String username = generateRandomUsername();
        String token = jwtTokenUtil.generateToken(username);

        return ResponseEntity.ok(new JwtResponse(token, username));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {

        if (jwtTokenUtil.isTokenExpired(request)) {
            throw new JWTException("토큰이 만료되었습니다");
        }
        return ResponseEntity.ok(true);

    }


    private String generateRandomUsername() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static String getHash(Map<String, List<Map<String, Object>>> body) {
        List<Map<String, Object>> fingerprint = body.get("fingerprint");

        String collect = fingerprint.stream().map(
                data -> (String) data.values().stream().findFirst().orElse("")
        ).collect(Collectors.joining());

        String hash = DigestUtils.sha256Hex(collect);
        return hash;
    }

}
