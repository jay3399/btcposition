package com.example.btcposition.application.ui.controller;

import com.example.btcposition.application.service.VoteApplicationService;
import com.example.btcposition.domain.vote.model.VoteDTO;
import com.example.btcposition.domain.vote.model.VoteType;
import com.example.btcposition.domain.votesummary.model.DailyResultDto;
import com.example.btcposition.application.ui.response.JwtResponse;
import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.aspect.VotedValidation;
import com.example.btcposition.domain.vote.service.VoteRedisServiceImpl;
import com.example.btcposition.infrastructure.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class MainController {


    private final VoteApplicationService voteService;
    private final JwtTokenUtil jwtTokenUtil;


    // request <-> service 독립적이여야한다 , 의존x 컨트롤러는 오직 요청에대한 검증과 응답만한다.
    @PostMapping("/vote/{value}")
    @VotedValidation
    public ResponseEntity<?> vote(@PathVariable @NotBlank String value,
            HttpServletRequest request) {

        VoteType voteType = VoteType.fromString(value);

        voteService.vote(voteType);

        JwtResponse jwtResponse = getJwtResponse(request);

        return ResponseEntity.ok(jwtResponse);

    }

    @GetMapping("/results")
    public ResponseEntity<List<VoteDTO>> getResults() {

        List<VoteDTO> voteResults = voteService.getResult();

        return ResponseEntity.ok(voteResults);
    }


    @GetMapping("/dailyResults")
    public ResponseEntity<?> getDailyResults(@RequestParam String month) {

        List<DailyResultDto> dailyResults = voteService.findDailyResult(month);

        return new ResponseEntity<>(dailyResults, HttpStatus.OK);
    }


    private JwtResponse getJwtResponse(HttpServletRequest request) {
        String jwtToken = jwtTokenUtil.getUpdatedToken(request);
        String username = jwtTokenUtil.getUsernameFromToken(request);

        return new JwtResponse(jwtToken, username);
    }


}

