package com.example.btcposition.service;

import com.example.btcposition.domain.Vote;
import java.util.List;

public interface VoteRedisService {

    List<Vote> getVoteResultV2();
    void processVote(String voteValue);

}
