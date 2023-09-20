package com.example.btcposition.infrastructure;


import com.example.btcposition.domain.vote.service.interfaces.HashRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTasks {

    private final HashRedisService redisService;

    private final RabbitTemplate rabbitTemplate;

    // 스케줄 관리.
    // 토큰이 만료되더라도 , 리프레시 토큰이라도 , 해당 해시값이 존재한다면 리프레시 불가. 중복토큰 발급불가.
    // 토큰 00:00:00 만료 , hash 00:00:00 만료.
    // 리프레시 하는 시점에서는 ( 토큰이 만료된 00:00:00이후 , hash 는 무조건 존재하지 않는다 , 검증필요업고 그냥 발행한다  )
    @Scheduled(cron = "0 */1 * * * *")
    public void synRedisWithMysql() {

        rabbitTemplate.convertAndSend("voteExchange", "vote", "synRedisWithMysql");


    }

    //   @Scheduled(cron = "59 59 23 * * ?")
    @Scheduled(cron = "0 */3 * * * *")
    public void summarizeDailyVotes() {

        rabbitTemplate.convertAndSend("voteExchange", "vote", "summarizeDailyVotes");

    }


    @Scheduled(cron = "0 */1 * * * *")
    public void resetHash() {

        System.out.println("키삭제");
        redisService.resetHash();

    }


}

//        try {
//            List<Vote> voteResults = redisService.getVoteResultsV2();
//
//            System.out.println("voteResults = " + voteResults);
//
//            voteService.updateVote(voteResults);
//
//        } catch (Exception e) {
//
//            throw new ScheduledTaskException(e);
//        }
//
//
//       voteService.summarizeVotes(); // vote -> summary 데이터 이동
//        redisService.deleteKeysByPreFix();