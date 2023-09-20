package com.example.btcposition.domain.vote.repository;

import com.example.btcposition.domain.vote.model.Vote;
import com.example.btcposition.domain.vote.model.VoteType;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByValue(VoteType value);

    @Modifying
    @Query("UPDATE Vote v SET v.count = :count WHERE v.value = :value")
    void updateVoteCount(@Param("value") VoteType voteValue, @Param("count") int voteCount);

    long countByValueAndVoteDate(VoteType value, LocalDate voteDate);

    @Query("select v.count from Vote v where v.value=:value")
    long getCount(@Param("value") VoteType voteType);

    void deleteByVoteDate(LocalDate voteDate);


}

