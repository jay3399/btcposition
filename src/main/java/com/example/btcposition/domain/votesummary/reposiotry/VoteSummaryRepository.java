package com.example.btcposition.domain.votesummary.reposiotry;

import com.example.btcposition.domain.votesummary.model.VoteSummary;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteSummaryRepository extends JpaRepository<VoteSummary, Long> {


    @Query("select v from VoteSummary v where v.date >:startDate AND v.date <=:endDate")
    List<VoteSummary> findAllByDate(@Param("startDate") LocalDate startValue, @Param("endDate") LocalDate endValue);



    @Query("select v.endPrice from VoteSummary v where v.date =:time")
    Optional<BigDecimal> findByDate(@Param("time") LocalDate time);





}
