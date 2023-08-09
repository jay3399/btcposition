package com.example.btcposition.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class VoteSummary {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;


    private long longCount;
    private long shortCount;


    private VoteSummary(LocalDate date, long longCount, long shortCount) {
        this.date = date;
        this.longCount = longCount;
        this.shortCount = shortCount;
    }

    public static VoteSummary create(LocalDate date , long longCount , long shortCount
    ) {
        VoteSummary voteSummary = new VoteSummary(date, longCount, shortCount);

        return voteSummary;

    }




}
