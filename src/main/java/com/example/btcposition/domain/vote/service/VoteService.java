package com.example.btcposition.domain.vote.service;

import com.example.btcposition.application.ui.response.JwtResponse;
import com.example.btcposition.infrastructure.util.JwtTokenUtil;
import com.example.btcposition.domain.voteSummary.model.DailyResultDto;
import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.voteSummary.model.VoteSummary;
import com.example.btcposition.domain.vote.model.VoteType;
import com.example.btcposition.domain.vote.repository.VoteRepository;
import com.example.btcposition.domain.voteSummary.reposiotry.VoteSummaryRepository;
import com.example.btcposition.infrastructure.BtcPriceProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    private final BtcPriceProvider btcPriceProvider;



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
    public void summarizeVotes() throws Exception {

        LocalDate now = LocalDate.now();
        long longCount = voteRepository.getCount(VoteType.LONG);
        long shortCount = voteRepository.getCount(VoteType.SHORT);

        Optional<BigDecimal> price = voteSummaryRepository.findByDate(now.minusDays(1));

        BigDecimal startPrice = price.orElse(new BigDecimal("20000"));

        VoteSummary voteSummary = VoteSummary.create(now, longCount, shortCount, startPrice,
                btcPriceProvider.getBtcPriceSync());

        voteSummaryRepository.save(voteSummary);


        voteRepository.updateVoteCount(VoteType.LONG, 0);
        voteRepository.updateVoteCount(VoteType.SHORT, 0);

    }


}
