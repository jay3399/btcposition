package com.example.btcposition.service;


import static com.example.btcposition.domain.VoteConstants.*;
import static com.example.btcposition.domain.VoteType.*;

import com.example.btcposition.domain.Vote;
import com.example.btcposition.domain.VoteConstants;
import com.example.btcposition.domain.VoteType;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    private static final String HASH_PREFIX = "hash:";
    private static final String VOTE_KEY_PREFIX = "vote:";


    public boolean isExist(String hash) {
        String value = (String) redisTemplate.opsForValue().get(HASH_PREFIX + hash);
        System.out.println("value = " + value);
        return value != null;
    }

    public void setValidate(String hash) {

        redisTemplate.opsForValue().set(HASH_PREFIX + hash, "valid", Duration.ofDays(1));


    }

//  @Scheduled(cron = "59 59 23 * * ?")

    @Scheduled(cron = "0 */1 * * * *")
    public void resetHash() {
        System.out.println("키삭제");

        Set keys = redisTemplate.keys(HASH_PREFIX + "*");

        keys.stream().forEach(
                key -> redisTemplate.delete(key)
        );

        System.out.println("키삭제");

//    if (keys != null) {
//      redisTemplate.delete(keys);
//      System.out.println("키삭제");
//    }

    }


    public List<Vote> getVoteResults() {

        List<Vote> voteResults = new ArrayList<>();

        String longValue = (String) redisTemplate.opsForValue().get(VOTE_KEY_PREFIX + LONG_VOTE);

        if (longValue != null) {
            Vote longVote = new Vote(LONG, Integer.parseInt(longValue));
            voteResults.add(longVote);
        }

        String shortValue = (String) redisTemplate.opsForValue().get(VOTE_KEY_PREFIX + SHORT_VOTE);

        if (shortValue != null) {
            Vote shortVote = new Vote(SHORT, Integer.parseInt(shortValue));
            voteResults.add(shortVote);
        }

        return voteResults;

    }

    public List<Vote> getVoteResultsV2() {
        List<Vote> voteResults = new ArrayList<>();

        for (VoteType voteType : VoteType.values()) {
            String value = (String) redisTemplate.opsForValue().get(VOTE_KEY_PREFIX + voteType.name());
            if (value != null) {
                Vote vote = new Vote(voteType, Integer.parseInt(value));
                voteResults.add(vote);
            }
        }

        return voteResults;
    }


    public void setVoteResult(String voteValue) {

        String key = VOTE_KEY_PREFIX + voteValue;
        String value = (String) redisTemplate.opsForValue().get(key);
        int count = value != null ? Integer.parseInt(value) : 0;
        count++;

        redisTemplate.opsForValue().set(key, String.valueOf(count));


    }

    public void setVoteResultV2(String voteValue) {
        VoteType voteType = VoteType.fromString(voteValue);
        String key = VOTE_KEY_PREFIX + voteType.name();
        String value = (String) redisTemplate.opsForValue().get(key);
        int count = value != null ? Integer.parseInt(value) : 0;
        count++;
        redisTemplate.opsForValue().set(key, String.valueOf(count));
    }


}
