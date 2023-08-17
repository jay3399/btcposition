package com.example.btcposition.service;

import com.example.btcposition.dto.JwtResponse;
import com.example.btcposition.JwtTokenUtil;
import com.example.btcposition.dto.DailyResultDto;
import com.example.btcposition.domain.Vote;
import com.example.btcposition.domain.VoteSummary;
import com.example.btcposition.domain.VoteType;
import com.example.btcposition.reposiotry.VoteRepository;
import com.example.btcposition.reposiotry.VoteSummaryRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteSummaryRepository voteSummaryRepository;
    private final JwtTokenUtil jwtTokenUtil;



    public JwtResponse generateJwtResponse(HttpServletRequest request) {
        String jwtToken = jwtTokenUtil.getUpdatedToken(request);
        String username = jwtTokenUtil.getUsernameFromToken(request);
        return new JwtResponse(jwtToken, username);
    }


    public Vote getVote(VoteType value) {
        return voteRepository.findByValue(value);

    }

    @Transactional
    public void saveVote(Vote vote) {

        voteRepository.save(vote);

    }

    @Transactional
    public void updateVote(List<Vote> votes) {

        for (Vote vote : votes) {
            System.out.println("vote = " + vote.getValue() + vote.getCount());
            voteRepository.updateVoteCount(vote.getValue(), vote.getCount());
        }
    }


    public List<Vote> findAll() {
        return voteRepository.findAll();

    }

    public List<DailyResultDto> findDailyResult(LocalDate date) {
        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());
        System.out.println("endDate = " + endDate);
        System.out.println("startDate = " + startDate);

        List<VoteSummary> voteSummaries = voteSummaryRepository.findAllByDate(startDate,
                endDate);

        System.out.println("voteSummaries = " + voteSummaries);

        return voteSummaries.stream().map(
                result -> {
                    System.out.println("result.getLongCount() = " + result.getLongCount());
                    return DailyResultDto.create(result);
                }

        ).collect(Collectors.toList());

    }


    @Transactional
    public void summarizeVotes() {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate now = LocalDate.now();

        long longCount = voteRepository.getCount(VoteType.LONG);
        long shortCount = voteRepository.getCount(VoteType.SHORT);

        System.out.println("shortCount = " + shortCount);
        System.out.println("longCount = " + longCount);

        VoteSummary voteSummary = VoteSummary.create(now, longCount, shortCount);

        voteSummaryRepository.save(voteSummary);

//        voteRepository.deleteByVoteDate(now);
        voteRepository.updateVoteCount(VoteType.LONG, 0);
        voteRepository.updateVoteCount(VoteType.SHORT, 0);

    }


}
