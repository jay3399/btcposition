package com.example.btcposition.reposiotry;

import com.example.btcposition.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findByValue(String value);

    @Modifying
    @Query("UPDATE Vote v SET v.count = :count WHERE v.value = :value")
    void updateVoteCount(@Param("value") String voteValue, @Param("count") int voteCount);


}

