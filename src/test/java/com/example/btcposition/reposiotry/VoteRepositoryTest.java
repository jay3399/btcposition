package com.example.btcposition.reposiotry;

import static org.junit.jupiter.api.Assertions.*;

import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.voteSummary.model.VoteSummary;
import com.example.btcposition.domain.vote.model.VoteType;
import com.example.btcposition.domain.vote.repository.VoteRepository;
import com.example.btcposition.domain.voteSummary.reposiotry.VoteSummaryRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest // 별도 트렌젝셔널 설정 필요없다 + 자동 롤백
@AutoConfigureTestDatabase(replace = Replace.NONE)
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteSummaryRepository voteSummaryRepository;

    @Autowired
    private TestEntityManager testEntityManager;




    @Test
    public void findByValue() {

        Vote vote = new Vote();

        vote.setValue(VoteType.LONG);

        testEntityManager.persist(vote);
        testEntityManager.flush();

        Vote found = voteRepository.findByValue(VoteType.LONG);

        assertEquals(vote, found);

    }

    @Test
    @Rollback(value = false)
    void saveSum() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate date = LocalDate.parse("2023-8-27", formatter);

        voteSummaryRepository.save(
                VoteSummary.create(date, 2, 2, new BigDecimal(10000), new BigDecimal(20000)));

    }





    @Test
    public void UpdateVoteCount() {


        Vote vote = new Vote();

        vote.setValue(VoteType.LONG);
        vote.setCount(3);

        testEntityManager.persist(vote);
        testEntityManager.flush();  // 영속성 컨텍스트 캐시 생성  , flush해도 그대로
        testEntityManager.clear(); //  clear 안해주면 , 캐시가 그대로 남아있어서 findValue 해도 해당값을 가져온다

        voteRepository.updateVoteCount(vote.getValue(), 10);



        Vote updated = voteRepository.findByValue(VoteType.LONG);


        assertEquals(10, updated.getCount());


    }




}