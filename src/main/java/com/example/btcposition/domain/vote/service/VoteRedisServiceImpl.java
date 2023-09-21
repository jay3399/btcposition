package com.example.btcposition.domain.vote.service;

import com.example.btcposition.domain.vote.model.VoteType;
import com.example.btcposition.exception.RedisCommunicationException;
import com.example.btcposition.domain.vote.service.interfaces.VoteRedisService;
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

    public static final String VOTE_KEY_PREFIX = "vote:";



    // 인프라는 오직 redis 와 통신하는 부분만을 담당한다, 도메인로직과 섞이면 안된다 .
    // 생성은 응용계

    @Override
    public void processVote(VoteType voteType) {


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
    public Integer getValueInRedis(VoteType voteType) {

        try {

            Integer value = executeWithRedis(key -> (Integer) redisTemplate.opsForValue().get(key),
                    VOTE_KEY_PREFIX + voteType.name());

            if (value == null) {
                throw new NullPointerException("레디스에 값이 존재하지 않습니다" + VOTE_KEY_PREFIX + voteType.name());
            }

            return value;

        } catch (DataAccessException e) {
            throw new RedisCommunicationException(e);
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



//    @Override
//    public List<Vote> getVoteResultV2() {
//        List<Vote> voteResults = new ArrayList<>();
//
//        for (VoteType voteType : VoteType.values()) {
//            addVoteResult(voteResults, voteType);
//        }
//
//        return voteResults;
//    }

//    private void addVoteResult(List<Vote> voteResults, VoteType voteType) {
//
//        //가져오는
//        Integer value = executeWithRedis(key -> (Integer) redisTemplate.opsForValue().get(key),
//                VOTE_KEY_PREFIX + voteType.name());
//
//        //생성하는
//        if (value != null) {
//            Vote vote = new Vote(voteType, value);
//            voteResults.add(vote);
//        }
//
//    }