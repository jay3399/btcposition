package com.example.btcposition.domain.vote.service.interfaces;

import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.vote.model.VoteType;
import java.util.List;

public interface VoteRedisService {

    void processVote(VoteType voteType);
    void deleteKeysByPreFix();

}
