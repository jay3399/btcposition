package com.example.btcposition.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class DailyResultDto {


    private LocalDate date;

    private long shortCount;
    private long longCount;

    private void set(VoteSummary voteSummary) {
        this.shortCount = voteSummary.getShortCount();
        this.longCount = voteSummary.getLongCount();
        this.date = voteSummary.getDate();
    }


    public static DailyResultDto create(VoteSummary voteSummary) {
        DailyResultDto dailyResultDto = new DailyResultDto();
        dailyResultDto.set(voteSummary);
        return dailyResultDto;
    }


}
