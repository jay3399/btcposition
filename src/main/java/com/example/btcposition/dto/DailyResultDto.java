package com.example.btcposition.dto;

import com.example.btcposition.domain.VoteSummary;
import com.example.btcposition.domain.VoteType;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class DailyResultDto {


    private LocalDate date;

    private long shortCount;
    private long longCount;

    private VoteType voteType;

    private void set(VoteSummary voteSummary) {
        this.shortCount = voteSummary.getShortCount();
        this.longCount = voteSummary.getLongCount();
        this.date = voteSummary.getDate();
        this.voteType = voteSummary.getWinningVoteType();
    }


    public static DailyResultDto create(VoteSummary voteSummary) {
        DailyResultDto dailyResultDto = new DailyResultDto();
        dailyResultDto.set(voteSummary);
        return dailyResultDto;
    }


}
