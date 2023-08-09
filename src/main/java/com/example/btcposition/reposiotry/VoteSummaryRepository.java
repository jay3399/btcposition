package com.example.btcposition.reposiotry;

import com.example.btcposition.domain.VoteSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteSummaryRepository extends JpaRepository<VoteSummary, Long> {



}
