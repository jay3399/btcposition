package com.example.btcposition.service;


import java.time.Duration;
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



  public boolean getValidate(String hash) {
    String value = (String) redisTemplate.opsForValue().get(HASH_PREFIX + hash);
    return value != null;
  }

  public void setValidate(String hash) {

    redisTemplate.opsForValue().set(HASH_PREFIX + hash, "valid", Duration.ofDays(1));


  }

  @Scheduled(cron = "59 59 23 * * ?")
  private void resetHash() {

    Set keys = redisTemplate.keys(HASH_PREFIX + "*");

    if (keys != null) {
      redisTemplate.delete(keys);
    }

  }





}
