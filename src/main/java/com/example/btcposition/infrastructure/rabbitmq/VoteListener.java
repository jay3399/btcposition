package com.example.btcposition.infrastructure.rabbitmq;

import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.exception.ScheduledTaskException;
import com.example.btcposition.domain.vote.service.VoteRedisServiceImpl;
import com.example.btcposition.infrastructure.BtcPriceProvider;
import com.example.btcposition.domain.vote.service.VoteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteListener {

    private final VoteRedisServiceImpl redisService;
    private final VoteService voteService;
    private final BtcPriceProvider btcPriceProvider;

    @RabbitListener(queues = "voteQueue")
    public void handleMessage(String message) throws Exception {
        if ("synRedisWithMysql".equals(message)) {
            try {
                List<Vote> voteResultsV2 = redisService.getVoteResultV2();
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
