package com.example.btcposition.reposiotry;

import static org.junit.jupiter.api.Assertions.*;

import com.example.btcposition.domain.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest // 별도 트렌젝셔널 설정 필요없다.
@AutoConfigureTestDatabase(replace = Replace.NONE)
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void findByValue() {

        Vote vote = new Vote();

        vote.setValue("value");

        testEntityManager.persist(vote);
        testEntityManager.flush();


        Vote found = voteRepository.findByValue(vote.getValue());

        assertEquals(vote, found);



    }


    @Test
    public void UpdateVoteCount() {

        String value = "value";

        Vote vote = new Vote();

        vote.setValue(value);
        vote.setCount(3);

        testEntityManager.persist(vote);
        testEntityManager.flush();  // 영속성 컨텍스트 캐시 생성  , flush해도 그대로
        testEntityManager.clear(); //  clear 안해주면 , 캐시가 그대로 남아있어서 findValue 해도 해당값을 가져온다

        voteRepository.updateVoteCount(vote.getValue(), 10);



        Vote updated = voteRepository.findByValue(value);


        assertEquals(10, updated.getCount());


    }




}