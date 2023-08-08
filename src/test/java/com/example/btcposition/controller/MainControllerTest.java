//package com.example.btcposition.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.example.btcposition.JwtTokenUtil;
//import com.example.btcposition.reposiotry.VoteRepository;
//import com.example.btcposition.service.RedisService;
//import com.example.btcposition.service.VoteService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//@WebMvcTest(MainController.class)
//class MainControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private VoteService voteService;
//
//  @MockBean
//  private RedisService redisService;
//
//  @MockBean
//  private VoteRepository voteRepository;
//
//  @MockBean
//  private JwtTokenUtil jwtTokenUtil;
//
//  @Test
//  void getVoteWithJWT() {
//  }
//

//  @Test
//  void vote() throws Exception {
//
//    when(jwtTokenUtil.getUpdatedToken(anyString())).thenReturn("new-jwt-token");
//
//    // jwtTokenUtil.isVoted() 호출을 false로 설정하여 이미 투표한 사용자가 아니라고 가정
//    when(jwtTokenUtil.isVoted(anyString())).thenReturn(false);
//
//    // 테스트용 URL
//    String url = "/vote/long";
//
//    // 요청 실행
//    mockMvc.perform(post(url)
//                    .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.token").value("new-jwt-token")); // 응답 내의 token 값 검증
//  }
//
//
//  @Test
//  void getResults() {
//  }
//
//  @Test
//  void generateToken() {
//  }
//
//  @Test
//  void isVoted() {
//  }
//
//  @Test
//  void synRedisWithMysql() {
//  }
//}