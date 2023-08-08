package com.example.btcposition.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.btcposition.domain.Vote;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.btcposition.reposiotry.VoteRepository;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;
    private VoteService voteService;


    @BeforeEach
    public void setUp() {
//        voteRepository = mock(VoteRepository.class);
        voteService = new VoteService(voteRepository);
    }


    @Test
    void getVote() {

        String value = "value";

        Vote expectedVote = mock(Vote.class);
//        VoteRepository voteRepository = mock(VoteRepository.class);

        when(voteRepository.findByValue(value)).thenReturn(expectedVote);

//        VoteService voteService1 = new VoteService(voteRepository);

        Vote result = voteService.getVote(value);

        assertEquals(expectedVote, result);
        verify(voteRepository).findByValue(value);




    }


    @Test
    @Transactional
    void saveVote() {
        Vote vote = mock(Vote.class);
//        VoteRepository voteRepository = mock(VoteRepository.class);
//        VoteService voteService = new VoteService(voteRepository);

        voteService.saveVote(vote);

        verify(voteRepository).save(vote);

    }

    @Test
    @Transactional
    void updateVote() {

//        VoteRepository voteRepository = mock(VoteRepository.class);
//        VoteService voteService = new VoteService(voteRepository);

        List<Vote> votes = new ArrayList<>();

        Vote vote = mock(Vote.class);

        vote.setValue("value");
        vote.setCount(1);
        votes.add(vote);

        voteService.updateVote(votes);

        verify(voteRepository).updateVoteCount(vote.getValue(), vote.getCount());



    }

    @Test
    void findAll() {

//        VoteRepository voteRepository = mock(VoteRepository.class);
//        VoteService voteService = new VoteService(voteRepository);

        List<Vote> votes = new ArrayList<>();

        when(voteRepository.findAll()).thenReturn(votes);

        List<Vote> foundVotes = voteService.findAll();

        assertEquals(votes, foundVotes);
        verify(voteRepository).findAll();



    }
}