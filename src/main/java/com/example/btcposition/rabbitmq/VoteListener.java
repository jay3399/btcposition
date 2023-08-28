package com.example.btcposition.rabbitmq;

import com.example.btcposition.domain.Vote;
import com.example.btcposition.exception.ScheduledTaskException;
import com.example.btcposition.service.BtcPriceProvider;
import com.example.btcposition.service.RedisService;
import com.example.btcposition.service.VoteService;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VoteListener {

    private final RedisService redisService;
    private final VoteService voteService;
    private final BtcPriceProvider btcPriceProvider;

    @RabbitListener(queues = "voteQueue")
    public void handleMessage(String message) throws Exception {
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
