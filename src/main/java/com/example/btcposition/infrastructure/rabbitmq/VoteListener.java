package com.example.btcposition.infrastructure.rabbitmq;

import com.example.btcposition.application.service.VoteApplicationService;
import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.vote.model.VoteDTO;
import com.example.btcposition.exception.ScheduledTaskException;
import com.example.btcposition.domain.vote.service.VoteRedisServiceImpl;
import com.example.btcposition.infrastructure.api.BtcPriceProvider;
import com.example.btcposition.domain.vote.service.VoteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteListener {

    private final VoteRedisServiceImpl redisService;
    private final VoteApplicationService voteApplicationService;
    private final VoteService voteService;
    private final BtcPriceProvider btcPriceProvider;

    @RabbitListener(queues = "voteQueue")
    public void handleMessage(String message) throws Exception {
        if ("synRedisWithMysql".equals(message)) {
            try {
                List<VoteDTO> voteResultsV2 = voteApplicationService.getResult();
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
