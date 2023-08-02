package com.example.btcposition.controller;

import com.example.btcposition.JwtResponse;
import com.example.btcposition.JwtTokenUtil;
import com.example.btcposition.domain.Vote;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.btcposition.service.voteService;


@Controller
@RequiredArgsConstructor
public class mainController {

  private final voteService voteService;
  private final JwtTokenUtil jwtTokenUtil;


//  @PostMapping("/position/vote/{value}")
//  @ResponseBody
//  public boolean getVote(@PathVariable String value) {
//
//    Vote vote = voteService.getVote(value);
//
//    if (vote == null) {
//      vote = new Vote(value, 1);
//    } else {
//      vote.setCount(vote.getCount() + 1);
//    }
//
//    System.out.println("vote = " + vote.getCount());
//
//
//    voteService.saveVote(vote);
//
//    return true;
//  }

  @PostMapping("/position/vote/{value}")
  @ResponseBody
  public ResponseEntity<?> getVoteWithJWT(@PathVariable String value , HttpServletRequest request) {


    String token = request.getHeader("Authorization");

    String username = jwtTokenUtil.getUsernameFromToken(token);
    System.out.println("username = " + username);

    if (jwtTokenUtil.isVoted(token)) {
      System.out.println("이미투표하였습니다");
      return ResponseEntity.badRequest().body("이미투표한사용자입니다");
    }

    Vote vote = voteService.getVote(value);

    if (vote == null){ vote = new Vote(value, 1);}
    else{ vote.setCount(vote.getCount()+1);}


    // if .. 사용자가 localstroge에서 jwt 값을 삭제시에는 ? -> 다시 재발급후 투표가 가능하다..




    voteService.saveVote(vote);


    String jwtToken = jwtTokenUtil.generateToken(username, true);

    return ResponseEntity.ok(new JwtResponse(jwtToken, username, true));
  }

//  @PostMapping("/position/vote/{value}")
//  @ResponseBody
//  public ResponseEntity<?> getVoteWithRedis(@PathVariable String value , HttpServletRequest request) {
//
//    String token = request.getHeader("Authorization");
//    String username = jwtTokenUtil.getUsernameFromToken(token);
//
//    // 사용자의 투표 여부를 확인
//    String voted = redisTemplate.opsForValue().get("voted:" + username);
//    if (voted != null && voted.equals("true")) {
//      System.out.println("이미 투표한 사용자입니다.");
//      return false;
//    }
//
//    // Redis에 투표한 사용자 정보 저장 (다음날 자정까지 유지)
//    LocalDateTime tomorrowMidnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
//    Duration duration = Duration.between(LocalDateTime.now(), tomorrowMidnight);
//    long secondsUntilTomorrowMidnight = duration.getSeconds();
//    redisTemplate.opsForValue().set("voted:" + username, "true", secondsUntilTomorrowMidnight, TimeUnit.SECONDS);
//
//    Vote vote = voteService.getVote(value);
//
//    if (vote == null) {
//      vote = new Vote(value, 1);
//    } else {
//      vote.setCount(vote.getCount() + 1);
//    }
//
//    voteService.saveVote(vote);
//
//    // JWT 토큰 생성하여 클라이언트에 전달
//    String jwtToken = jwtTokenUtil.generateToken(username, secondsUntilTomorrowMidnight);
//    return new ResponseEntity<>(new JwtResponse(jwtToken), HttpStatus.OK);
//
//
//  }

  @GetMapping("/position/results")
  @ResponseBody
  public ResponseEntity<List<Vote>> getResults() {
    List<Vote> voteResults = voteService.findAll();
    return ResponseEntity.ok(voteResults);
  }


  @PostMapping("/getJwtToken")
  @ResponseBody
  public ResponseEntity<JwtResponse> getJwtToken(@RequestBody Map<String ,List<Map<String , Object>>> body) {




    String username = generateRandomUsername();
    System.out.println("username = " + username);
    String token = jwtTokenUtil.generateToken(username,false);
    System.out.println("token = " + token);

    List<Map<String, Object>> fingerprint = body.get("fingerprint");

    List<Object> collect = fingerprint.stream().flatMap(
        data -> data.values().stream()
    ).collect(Collectors.toList());




    System.out.println("fingerprint = " + fingerprint);
    System.out.println("collect = " + collect);

    // 객체에 Getter 없을시 , not accept 406오류.

    return ResponseEntity.ok(new JwtResponse(token, username, false));

  }

  private String generateRandomUsername() {
    return UUID.randomUUID().toString().replace("-", "");
  }


}
