package com.example.btcposition.service;

import com.example.btcposition.domain.Vote;
import com.example.btcposition.reposiotry.VoteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;


    public Vote getVote(String value) {
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


}
