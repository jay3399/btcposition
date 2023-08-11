package com.example.btcposition.service;


import com.example.btcposition.domain.Vote;
import com.example.btcposition.exception.ScheduledTaskException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {

    private final RedisService redisService;
    private final VoteService voteService;

    // 스케줄 관리.

    @Scheduled(cron = "0 */3 * * * *")
    public void synRedisWithMysql() {

        try {
            List<Vote> voteResults = redisService.getVoteResultsV2();

            voteService.updateVote(voteResults);

        } catch (Exception e) {

            throw new ScheduledTaskException(e);
        }

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void summarizeVotes() {
        voteService.summarizeVotes();
    }

    //  @Scheduled(cron = "59 59 23 * * ?")
    @Scheduled(cron = "0 */1 * * * *")
    public void resetHash() {

        redisService.resetHash();

    }


}
