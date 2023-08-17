package com.example.btcposition;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private @Value("${spring.data.redis.host}") String redisHost;
    private @Value("${spring.data.redis.port}") int redisPort;

    private long timeout;

    private int maxActive;

    private int maxIdle;

    private int minIdle;


    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);

//        LettuceClientConfiguration configuration = LettuceClientConfiguration.builder()
//                .commandTimeout(Duration.ofMillis(timeout)).readFrom(
//                        ReadFrom.MASTER_PREFERRED).build();

//        return LettuceConnectionFactory(redisHost, redisPort, configuration);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory());
        return redisTemplate;
    }


}
