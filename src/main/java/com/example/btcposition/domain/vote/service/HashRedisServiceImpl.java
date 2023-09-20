package com.example.btcposition.domain.vote.service;

import com.example.btcposition.exception.AlreadyHashException;
import com.example.btcposition.exception.RedisCommunicationException;
import com.example.btcposition.domain.vote.service.interfaces.HashRedisService;
import java.time.Duration;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashRedisServiceImpl implements HashRedisService {

    private final RedisTemplate redisTemplate;

    public static final String HASH_PREFIX = "hash:";
    public static final String VOTE_KEY_PREFIX = "vote:";

    @Override
    public void isExist(String hash) throws Exception {

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
    public void resetHash() {
        Set keys = redisTemplate.keys(HASH_PREFIX + "*");

        keys.forEach(key -> redisTemplate.delete(key));
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
