package com.example.btcposition.service;


import com.example.btcposition.domain.Vote;
import com.example.btcposition.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final VoteService voteService;

  @Override
  public void run(String... args) throws Exception {

    Vote longVote = new Vote("long", 0);
    voteService.saveVote(longVote);

    Vote shortVote = new Vote("short", 0);
    voteService.saveVote(shortVote);

  }
}