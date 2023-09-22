package com.example.btcposition.application.service;

import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.vote.model.VoteDTO;
import com.example.btcposition.domain.vote.model.VoteType;
import com.example.btcposition.domain.vote.service.VoteRedisServiceImpl;
import com.example.btcposition.domain.vote.service.VoteService;
import com.example.btcposition.domain.vote.service.interfaces.VoteRedisService;
import com.example.btcposition.domain.votesummary.model.DailyResultDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteApplicationService {

    private final VoteService voteService;
    private final VoteRedisService redisService;


    public void vote( VoteType voteType) {

        redisService.processVote(voteType);

    }

    public List<VoteDTO> getResult() {

        List<VoteDTO> result = new ArrayList<>();

        for (VoteType voteType : VoteType.values()) {

            Integer value = redisService.getValueInRedis(voteType);

                result.add(VoteDTO.create(voteType, value));
        }

        return result;

    }

    public List<DailyResultDto> findDailyResult(String month) {

        LocalDate date = LocalDate.parse(month + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        return voteService.findDailyResult(date);

    }



}
