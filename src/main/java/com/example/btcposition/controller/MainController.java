package com.example.btcposition.controller;

import com.example.btcposition.dto.DailyResultDto;
import com.example.btcposition.dto.JwtResponse;
import com.example.btcposition.domain.Vote;
import com.example.btcposition.service.RedisServiceImpl;
import com.example.btcposition.aspect.VotedValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.btcposition.service.VoteService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class MainController {

    private final VoteService voteService;

    private final RedisServiceImpl redisService;


    @PostMapping("/vote/{value}")
    @VotedValidation
    public ResponseEntity<?> vote(@PathVariable @NotBlank String value,
            HttpServletRequest request) {

        redisService.processVote(value);

        JwtResponse jwtResponse = voteService.generateJwtResponse(request);

        return ResponseEntity.ok(jwtResponse);

//    컨트롤러단에서 token을 받아서 보내주는것보다 ,requset를 보내서 token을 내부적으로 사용하는 Util 클래스에서 만들도록 하는게 더 응집성에 맘ㅈ다.
    }

//

    @GetMapping("/results")
    public ResponseEntity<List<Vote>> getResults() {
        List<Vote> voteResults = redisService.getVoteResultV2();
        return ResponseEntity.ok(voteResults);
    }


    @GetMapping("/dailyResults")
    public ResponseEntity<?> getDailyResults(@RequestParam String month) {

        LocalDate date = LocalDate.parse(month + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<DailyResultDto> dailyResults = voteService.findDailyResult(date);
        return new ResponseEntity<>(dailyResults, HttpStatus.OK);
    }


}

//    private String generateRandomUsername() {
//        return UUID.randomUUID().toString().replace("-", "");
//    }
//

//        validateVote(request);

//        if (jwtTokenUtil.isVoted(request)) {
//            throw new AlreadyVotedException();
//        }

//        processVote(value);

//    private void validateVote(HttpServletRequest request) {
//        if (jwtTokenUtil.isVoted(request)) {
//            throw new AlreadyVotedException();
//        }
//    }

//    private void processVote(String value) {
//        try {
//            redisService.processVote(value);
//        } catch (Exception e) {
//            throw new RedisCommunicationException(e);
//        }
//    }

//@GetMapping("/checkVoted")
//    public ResponseEntity<?> isVoted(HttpServletRequest request) {
//
//        if (jwtTokenUtil.isVoted(request)) {
//            System.out.println("이미투표하였습니다");
//            return ResponseEntity.badRequest().body("이미투표한사용자입니다");
//        }
//
//        return ResponseEntity.ok(true);
//    }

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
//  @ResponseBody
//  public ResponseEntity<?> getVoteWithJWT(@PathVariable String value , HttpServletRequest request) {
//
//
//
//    String username = jwtTokenUtil.getUsernameFromToken(request);
//
//    if (jwtTokenUtil.isVoted(request)) {
//      System.out.println("이미투표하였습니다");
//      return ResponseEntity.badRequest().body("이미투표한사용자입니다");
//    }
//
//    Vote vote = voteService.getVote(value);
//
//    if (vote == null){ vote = new Vote(value, 1);}
//    else{ vote.setCount(vote.getCount()+1);}
//
//
//    // if .. 사용자가 localstroge에서 jwt 값을 삭제시에는 ? -> 다시 재발급후 투표가 가능하다..
//    voteService.saveVote(vote);
//
//
//    String jwtToken = jwtTokenUtil.generateToken(username);
//
//    return ResponseEntity.ok(new JwtResponse(jwtToken, username));
//  }

//  @GetMapping("/position/results")
//  @ResponseBody
//  public ResponseEntity<List<Vote>> getResults() {
//    List<Vote> voteResults = voteService.findAll();
//    return ResponseEntity.ok(voteResults);
//  }