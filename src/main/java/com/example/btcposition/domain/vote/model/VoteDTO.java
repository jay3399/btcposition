package com.example.btcposition.domain.vote.model;

import jakarta.persistence.Column;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class VoteDTO {

    private VoteType value;
    private int count;

    private VoteDTO(VoteType value, int count) {
        this.value = value;
        this.count = count;
    }
    public static VoteDTO create(VoteType value, int count) {
        return new VoteDTO(value, count);
    }

}
