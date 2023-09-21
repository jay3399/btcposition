package com.example.btcposition.domain.vote.service;

import com.example.btcposition.domain.vote.model.VoteDTO;
import com.example.btcposition.domain.votesummary.model.DailyResultDto;
import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.votesummary.model.VoteSummary;
import com.example.btcposition.domain.vote.model.VoteType;
import com.example.btcposition.domain.vote.repository.VoteRepository;
import com.example.btcposition.domain.votesummary.reposiotry.VoteSummaryRepository;
import com.example.btcposition.infrastructure.api.BtcPriceProvider;
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
    private final BtcPriceProvider btcPriceProvider;


    @Transactional
    public void saveVote(Vote vote) {

        voteRepository.save(vote);

    }

    @Transactional
    public void updateVote(List<VoteDTO> votes) {

        for (VoteDTO vote : votes) {
            voteRepository.updateVoteCount(vote.getValue(), vote.getCount());
        }
    }


    public List<Vote> findAll() {
        return voteRepository.findAll();

    }

    public List<DailyResultDto> findDailyResult(LocalDate date) {

        LocalDate startDate = date.withDayOfMonth(1);
        LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());

        List<VoteSummary> voteSummaries = voteSummaryRepository.findAllByDate(startDate, endDate);

        return voteSummaries.stream().map(
                (result) -> DailyResultDto.create(result)
        ).collect(Collectors.toList());

    }


    @Transactional
    public void summarizeVotes() throws Exception {

        LocalDate now = LocalDate.now();

        long longCount = voteRepository.getCount(VoteType.LONG);
        long shortCount = voteRepository.getCount(VoteType.SHORT);

        Optional<BigDecimal> price = voteSummaryRepository.findByDate(now.minusDays(1));

        BigDecimal startPrice = price.orElse(new BigDecimal("20000"));

        VoteSummary voteSummary = VoteSummary.create(now, longCount, shortCount, startPrice, btcPriceProvider.getBtcPriceSync());

        voteSummaryRepository.save(voteSummary);


        voteRepository.updateVoteCount(VoteType.LONG, 0);
        voteRepository.updateVoteCount(VoteType.SHORT, 0);

    }


}
