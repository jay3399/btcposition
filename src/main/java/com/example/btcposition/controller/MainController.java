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
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.btcposition.service.VoteService;
import com.example.btcposition.service.RedisService;



@Controller
@RequiredArgsConstructor
public class MainController {

  private final VoteService voteService;

  private final RedisService redisService;

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

//  @PostMapping("/position/vote/{value}")
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

  @PostMapping("/position/vote/{value}")
  @ResponseBody
  public ResponseEntity<?> getVoteWithRedis(@PathVariable String value , HttpServletRequest request) {


    String token = request.getHeader("Authorization");

    String username = jwtTokenUtil.getUsernameFromToken(token);
    System.out.println("username = " + username);

    if (jwtTokenUtil.isVoted(token)) {
      System.out.println("이미투표하였습니다");
      return ResponseEntity.badRequest().body("이미투표한사용자입니다");
    }

    redisService.setVoteResult(value);


    String jwtToken = jwtTokenUtil.generateToken(username, true);

    return ResponseEntity.ok(new JwtResponse(jwtToken, username, true));
  }


//  @GetMapping("/position/results")
  @ResponseBody
  public ResponseEntity<List<Vote>> getResults() {
    List<Vote> voteResults = voteService.findAll();
    return ResponseEntity.ok(voteResults);
  }

  @GetMapping("/position/results")
  @ResponseBody
  public ResponseEntity<List<Vote>> getResultsWithRedis() {
    List<Vote> voteResults = redisService.getVoteResults();
    return ResponseEntity.ok(voteResults);
  }



  @PostMapping("/getJwtToken")
  @ResponseBody
  public ResponseEntity<?> getJwtToken(@RequestBody Map<String, List<Map<String, Object>>> body, HttpServletRequest request) {


    List<Map<String, Object>> fingerprint = body.get("fingerprint");

    String collect = fingerprint.stream().map(
        data -> (String) data.values().stream().findFirst().orElse("")
    ).collect(Collectors.joining());

    String hash = DigestUtils.sha256Hex(collect);


    //  getValidate 에서 , redis내부에서 해당 hash값이 존재하면
    if (redisService.isExist(hash)) {
      return ResponseEntity.badRequest().build();
    }

    // 존재하지 않을시 -> 토큰발급 후 , 해당 해시값 redis에 저장 .
    redisService.setValidate(hash);



    String username = generateRandomUsername();
    String token = jwtTokenUtil.generateToken(username, false);


    // 객체에 Getter 없을시 , not accept 406오류.

    return ResponseEntity.ok(new JwtResponse(token, username, false));

  }

  @Scheduled(cron = "0 */3 * * * *" )
  public void saveRedisToMysql() {

    List<Vote> voteResults = redisService.getVoteResults();

    voteService.updateVote(voteResults);


  }





  private String generateRandomUsername() {
    return UUID.randomUUID().toString().replace("-", "");
  }


}
