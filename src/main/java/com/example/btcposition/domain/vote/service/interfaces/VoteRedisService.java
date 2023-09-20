package com.example.btcposition.domain.vote.service.interfaces;

import com.example.btcposition.domain.vote.model.Vote;
import java.util.List;

public interface VoteRedisService {

    List<Vote> getVoteResultV2();
    void processVote(String voteValue);
    void deleteKeysByPreFix();

}
