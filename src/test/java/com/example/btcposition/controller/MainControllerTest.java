//package com.example.btcposition.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.example.btcposition.JwtTokenUtil;
//import com.example.btcposition.reposiotry.VoteRepository;
//import com.example.btcposition.service.RedisService;
//import com.example.btcposition.service.VoteService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
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
//
////  @BeforeEach
////  public void setUp() {
////    doNothing().when(redisService).isExist(null);
////  }
//
//
//  @Test
//  public void testVoteWithoutToken() throws Exception {
//    // JWT 토큰이 없을 때의 테스트
//    mockMvc.perform(post("/vote")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .param("value", "long"))
//                    .andExpect(redirectedUrl("/token"));
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