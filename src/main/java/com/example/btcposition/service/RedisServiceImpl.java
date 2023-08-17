package com.example.btcposition.service;

import com.example.btcposition.domain.Vote;
import com.example.btcposition.domain.VoteType;
import com.example.btcposition.exception.AlreadyHashException;
import com.example.btcposition.exception.RedisCommunicationException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements VoteRedisService, HashRedisService {

    private final RedisTemplate redisTemplate;

    public static final String HASH_PREFIX = "hash:";
    public static final String VOTE_KEY_PREFIX = "vote:";

    @Override
    public void isExist(String hash) {

        String value = executeWithRedis((key) -> (String) redisTemplate.opsForValue().get(key),
                HASH_PREFIX + hash);

        if (value != null) {
            throw new AlreadyHashException();
        }

    }

    @Override
    public void setValidate(String hash) {

        executeWithRedisWithConsumer(
                key -> redisTemplate.opsForValue().set(key, "vauld", Duration.ofDays(1)),
                HASH_PREFIX + hash);

    }

    @Override
    public void resultHash() {

        Set keys = redisTemplate.keys(HASH_PREFIX + "*");

        keys.forEach(key -> redisTemplate.delete(key));

    }

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


//ISP
//클라이언트가 사용하지않는 메서드에 의존하지 않아야한다.

//예외처리로직 , Controller to Service

// try catch 가독성개선 with 함수형인터페이스

// Callable 의 Call 메서드는 값을 반환한다 <-> Runnable  but 체크예외를 던진다
// Function 명시적으로 input 을 넣을수있고 , 체크예외를 안던져도 괜찮다 , 하지만 반환값을 던져야한다
// Consumer 는 소모만하고 값을 반환하지 않는다.


//    체크예외를 던져야한다
//    private <T> T executeWithRedis(Callable<T> callable) throws Exception {
//        try {
//            return callable.call();
//        } catch (DataAccessException e) {
//            throw new RedisCommunicationException(e);
//        }
//    }

//        try {
//            String value = (String) redisTemplate.opsForValue().get(HASH_PREFIX + hash);
//            if (value != null) {
//                throw new AlreadyHashException();
//            }
//        } catch (AlreadyHashException e) {
//            throw e;
//        } catch (DataAccessException e) {
//            throw new RedisCommunicationException(e);
//        }
//        try {
//            redisTemplate.opsForValue().set(HASH_PREFIX + hash, "valid", Duration.ofDays(1));
//        } catch (DataAccessException e) {
//            throw new RedisCommunicationException(e);
//        }

//        try {
//            redisTemplate.opsForValue().set(HASH_PREFIX + hash, "valid", Duration.ofDays(1));
//        } catch (DataAccessException e) {
//            throw new RedisCommunicationException(e);
//        }
//        try {
//            Integer value = (Integer) redisTemplate.opsForValue().get(VOTE_KEY_PREFIX + voteType.name());
//            if (value != null) {
//                Vote vote = new Vote(voteType, value);
//                voteResults.add(vote);}
//        }
//        catch (DataAccessException e) {
//            throw new RedisCommunicationException(e);
//        }