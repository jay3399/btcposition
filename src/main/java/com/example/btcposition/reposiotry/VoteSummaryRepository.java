package com.example.btcposition.reposiotry;

import com.example.btcposition.domain.VoteSummary;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteSummaryRepository extends JpaRepository<VoteSummary, Long> {


    @Query("select v from VoteSummary v where v.date >:startDate AND v.date <=:endDate")
    List<VoteSummary> findAllByDate(@Param("startDate") LocalDate startValue, @Param("endDate") LocalDate endValue);

}
