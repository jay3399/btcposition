package com.example.btcposition.domain.votesummary.model;

import com.example.btcposition.domain.vote.model.VoteType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class VoteSummary {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;


    private long longCount;
    private long shortCount;

    private BigDecimal startPrice;
    private BigDecimal endPrice;

    @Enumerated(value = EnumType.STRING)
    private VoteType winningVoteType;


    private VoteSummary(LocalDate date, long longCount, long shortCount , BigDecimal startPrice , BigDecimal endPrice , VoteType winningVoteType) {
        this.date = date;
        this.longCount = longCount;
        this.shortCount = shortCount;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.winningVoteType = winningVoteType;
    }

    public static VoteSummary create(LocalDate date, long longCount, long shortCount , BigDecimal startPrice , BigDecimal endPrice
    ) {

        VoteType winningVoteType = (startPrice.compareTo(endPrice) <= 0) ? VoteType.LONG : VoteType.SHORT;

        VoteSummary voteSummary = new VoteSummary(date, longCount, shortCount, startPrice, endPrice, winningVoteType);

        return voteSummary;

    }


}
