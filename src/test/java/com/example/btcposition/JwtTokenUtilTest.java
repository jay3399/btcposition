package com.example.btcposition;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;


@SpringBootTest
@TestPropertySource(properties = "jwt.secret=6ouCAVlTs2RpGKctFeqtKDC3IzQfIuNSZogfQuGYgvo")
class JwtTokenUtilTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private HttpServletRequest request;

    @BeforeEach
    public void setup() {
//        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateToken() {
        String username = "user";
        String token = jwtTokenUtil.generateToken(username);
        assertNotNull(token);
    }

    @Test
    public void getUsernameFromToken() {
        String username = "user";
        String token = "Bearer " + jwtTokenUtil.generateToken(username);
        when(request.getHeader("Authorization")).thenReturn(token);
        assertEquals(username, jwtTokenUtil.getUsernameFromToken(request));
    }

    @Test
    public void getUpdatedToken() {
        String token = "Bearer " + jwtTokenUtil.generateToken("user");  // 토큰발급
        when(request.getHeader("Authorization")).thenReturn(token);     // getHeader 토큰추출시 ,  위 토큰 발행
        String updatedToken = jwtTokenUtil.getUpdatedToken(request);    // 위토큰 발행해서 , 업데이트후 반환
        assertNotNull(updatedToken);                                    // 토큰 발행 확인
//        assertTrue(jwtTokenUtil.isVoted(request));                      // 위의 , when 의 업데이트 안된 토큰이 쓰이고있으니 당연히 false 가 뜬다.

        when(request.getHeader("Authorization")).thenReturn("Bearer " + updatedToken);
        assertTrue(jwtTokenUtil.isVoted(request));
    }

//    @Test
//    public void isTokenExpired() {
//        assertFalse(jwtTokenUtil.isTokenExpired(request));
//    }

    @Test
    public void isVoted() {
        String token = "Bearer " + jwtTokenUtil.generateToken("user");
        when(request.getHeader("Authorization")).thenReturn(token);
        assertFalse(jwtTokenUtil.isVoted(request));
    }

}