package com.example.btcposition.service;

import static com.example.btcposition.domain.VoteType.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.btcposition.domain.Vote;
import com.example.btcposition.domain.VoteSummary;
import com.example.btcposition.domain.VoteType;
import com.example.btcposition.reposiotry.VoteSummaryRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.btcposition.reposiotry.VoteRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

@SpringBootTest
class VoteServiceTest {

    @MockBean
    private VoteRepository voteRepository;

    @Autowired
    private VoteSummaryRepository repository;

    @Autowired
    private VoteService voteService;


//    @BeforeEach
//    public void setUp() {
////        voteRepository = mock(VoteRepository.class);
//        voteService = new VoteService(voteRepository);
//    }




    @Test
    void getVote() {

        String value = "value";

        Vote expectedVote = mock(Vote.class);

        when(voteRepository.findByValue(LONG)).thenReturn(expectedVote);


        Vote result = voteService.getVote(LONG);

        assertEquals(expectedVote, result);
        verify(voteRepository).findByValue(LONG);


    }




    @Test
    @Transactional
    void saveVote() {
        Vote vote = mock(Vote.class);

        voteService.saveVote(vote);

        verify(voteRepository).save(vote);

    }

    @Test
    @Transactional
    void updateVote() {

        List<Vote> votes = new ArrayList<>();

        Vote vote = mock(Vote.class);

        vote.setValue(LONG);
        vote.setCount(1);
        votes.add(vote);

        voteService.updateVote(votes);

        verify(voteRepository).updateVoteCount(vote.getValue(), vote.getCount());


    }

    @Test
    void findAll() {

        List<Vote> votes = new ArrayList<>();

        when(voteRepository.findAll()).thenReturn(votes);

        List<Vote> foundVotes = voteService.findAll();

        assertEquals(votes, foundVotes);
        verify(voteRepository).findAll();


    }
}