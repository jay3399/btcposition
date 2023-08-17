package com.example.btcposition.service;


import com.example.btcposition.domain.Vote;
import com.example.btcposition.domain.VoteType;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate redisTemplate;

    public static final String HASH_PREFIX = "hash:";
    public static final String VOTE_KEY_PREFIX = "vote:";


    public void deleteKeysByPreFix() {
        Set<String> keys = redisTemplate.keys(VOTE_KEY_PREFIX + "*");

        keys.forEach(
                key ->
                        redisTemplate.delete(key)
        );

    }


    public void resetHash() {

        Set keys = redisTemplate.keys(HASH_PREFIX + "*");

        keys.forEach(key -> redisTemplate.delete(key));

    }

    public List<Vote> getVoteResultsV2() {
        List<Vote> voteResults = new ArrayList<>();

        for (VoteType voteType : VoteType.values()) {
            addVoteResult(voteResults, voteType);
        }

        return voteResults;
    }

    public void setVoteResultV2(String voteValue) {
        VoteType voteType = VoteType.fromString(voteValue);
        String key = VOTE_KEY_PREFIX + voteType.name();

        if (redisTemplate.opsForValue().get(key) == null) {
            redisTemplate.opsForValue().set(key, 0);
        }

        redisTemplate.opsForValue().increment(key);
    }


    private void addVoteResult(List<Vote> voteResults, VoteType voteType) {
        Integer value = (Integer) redisTemplate.opsForValue()
                .get(VOTE_KEY_PREFIX + voteType.name());
        if (value != null) {
            Vote vote = new Vote(voteType, value);
            voteResults.add(vote);
        }
    }


}

//레디스는 기본적으로 단일 스레드모델이지만 , 클라이언트가 동시에 접속해서 특정키에대한 동시업데이트문제가 될수있다
//투표수를 count++ 하고 다시저장하는 과정에서 동시성 문제가 발생할수있음
//redis 의 incr 명령은 특정키의 값을 원자적으로 증가시키므로 동시성문제를 해결가능.
//동시에 투표를하더라도 , 순차적으로 처리하기떄문에 문제가 안생김
//    String value = (String) redisTemplate.opsForValue().get(key);
//    int count = value != null ? Integer.parseInt(value) : 0;
//    count++;
//    public boolean isExist(String hash) {
//        String value = (String) redisTemplate.opsForValue().get(HASH_PREFIX + hash);
//        log.debug("value={}", value);
//        return value != null;
//    }
//
//    public void setValidate(String hash) {
//        redisTemplate.opsForValue().set(HASH_PREFIX + hash, "valid", Duration.ofDays(1));
//    }
//    public void setVoteResult(String voteValue) {
//
//        String key = VOTE_KEY_PREFIX + voteValue;
//        String value = (String) redisTemplate.opsForValue().get(key);
//        int count = value != null ? Integer.parseInt(value) : 0;
//        count++;
//
//        redisTemplate.opsForValue().set(key, String.valueOf(count));
//

//    }

//    public List<Vote> getVoteResults() {
//
//        List<Vote> voteResults = new ArrayList<>();
//
//        String longValue = (String) redisTemplate.opsForValue().get(VOTE_KEY_PREFIX + LONG_VOTE);
//
//        if (longValue != null) {
//            Vote longVote = new Vote(LONG, Integer.parseInt(longValue));
//            voteResults.add(longVote);
//        }
//
//        String shortValue = (String) redisTemplate.opsForValue().get(VOTE_KEY_PREFIX + SHORT_VOTE);
//
//        if (shortValue != null) {
//            Vote shortVote = new Vote(SHORT, Integer.parseInt(shortValue));
//            voteResults.add(shortVote);
//        }
//
//        return voteResults;
//
//    }