package com.example.btcposition.service;

import com.example.btcposition.domain.Vote;
import com.example.btcposition.domain.VoteSummary;
import com.example.btcposition.domain.VoteType;
import com.example.btcposition.reposiotry.VoteRepository;
import com.example.btcposition.reposiotry.VoteSummaryRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteSummaryRepository voteSummaryRepository;


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
            voteRepository.updateVoteCount(vote.getValue(), vote.getCount());
        }
    }


    public List<Vote> findAll() {
        return voteRepository.findAll();

    }


    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void summarizeVotes() {

        LocalDate yesterday = LocalDate.now().minusDays(1);

        long longCount = voteRepository.countByValueAndVoteDate(VoteType.LONG, yesterday);
        long shortCount = voteRepository.countByValueAndVoteDate(VoteType.SHORT, yesterday);

        VoteSummary voteSummary = VoteSummary.create(yesterday, longCount, shortCount);

        voteSummaryRepository.save(voteSummary);

        voteRepository.deleteByVoteDate(yesterday);

    }


}
