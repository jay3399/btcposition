package com.example.btcposition.domain.vote.service.interfaces;

import com.example.btcposition.domain.vote.model.VoteType;

public interface VoteRedisService {

    void processVote(VoteType voteType);
    void deleteKeysByPreFix();
    Integer getValueInRedis(VoteType voteType);

}
