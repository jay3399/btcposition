package com.example.btcposition.reposiotry;

import com.example.btcposition.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface voteRepository extends JpaRepository<Vote, Long> {

  Vote findByValue(String value);


}

