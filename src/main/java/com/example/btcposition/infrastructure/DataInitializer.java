package com.example.btcposition.infrastructure;


import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.vote.model.VoteType;
import com.example.btcposition.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final VoteService voteService;

    @Override
    public void run(String... args) throws Exception {

        Vote longVote = new Vote(VoteType.LONG, 0);
        voteService.saveVote(longVote);
        Vote shortVote = new Vote(VoteType.SHORT, 0);
        voteService.saveVote(shortVote);

    }
}

