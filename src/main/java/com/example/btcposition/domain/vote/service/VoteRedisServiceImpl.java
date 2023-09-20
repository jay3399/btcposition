package com.example.btcposition.domain.vote.service;

import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.vote.model.VoteType;
import com.example.btcposition.exception.RedisCommunicationException;
import com.example.btcposition.domain.vote.service.interfaces.VoteRedisService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteRedisServiceImpl implements VoteRedisService {

    private final RedisTemplate redisTemplate;

    public static final String HASH_PREFIX = "hash:";
    public static final String VOTE_KEY_PREFIX = "vote:";


    @Override
    public List<Vote> getVoteResultV2() {
        List<Vote> voteResults = new ArrayList<>();

        for (VoteType voteType : VoteType.values()) {
            addVoteResult(voteResults, voteType);
        }

        return voteResults;
    }

    @Override
    public void processVote(String voteValue) {

        VoteType voteType = VoteType.fromString(voteValue);

        String value = VOTE_KEY_PREFIX + voteType.name();

        executeWithRedisWithConsumer(
                key -> {
                    if (redisTemplate.opsForValue().get(key) == null) {
                        redisTemplate.opsForValue().set(key, 0);
                    }
                    redisTemplate.opsForValue().increment(key);
                }, value
        );

    }

    @Override
    public void deleteKeysByPreFix() {

        Set<String> keys = redisTemplate.keys(VOTE_KEY_PREFIX + "*");

        keys.forEach(key -> redisTemplate.delete(key));


    }

    private void addVoteResult(List<Vote> voteResults, VoteType voteType) {

        Integer value = executeWithRedis(key -> (Integer) redisTemplate.opsForValue().get(key),
                VOTE_KEY_PREFIX + voteType.name());

        if (value != null) {
            Vote vote = new Vote(voteType, value);
            voteResults.add(vote);
        }

    }

    private <T, R> R executeWithRedis(Function<T, R> function, T input) {
        try {
            return function.apply(input);
        } catch (DataAccessException e) {
            throw new RedisCommunicationException(e);
        }
    }

    private <T> void executeWithRedisWithConsumer(Consumer<T> consumer, T input) {
        try {
            consumer.accept(input);
        } catch (DataAccessException e) {
            throw new RedisCommunicationException(e);
        }
    }
}
