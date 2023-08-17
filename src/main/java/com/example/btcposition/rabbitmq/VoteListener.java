package com.example.btcposition.rabbitmq;

import com.example.btcposition.domain.Vote;
import com.example.btcposition.exception.ScheduledTaskException;
import com.example.btcposition.service.RedisService;
import com.example.btcposition.service.VoteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteListener {

    private final RedisService redisService;

    private final VoteService voteService;


    @RabbitListener(queues = "voteQueue")
    public void handleMessage(String message) {
        if ("synRedisWithMysql".equals(message)) {
            try {
                List<Vote> voteResultsV2 = redisService.getVoteResultsV2();
                voteService.updateVote(voteResultsV2);
            } catch (Exception e) {
                throw new ScheduledTaskException(e);
            }
        } else if ("summarizeDailyVotes".equals(message)) {
            voteService.summarizeVotes();
            redisService.deleteKeysByPreFix();
        }
    }


}
