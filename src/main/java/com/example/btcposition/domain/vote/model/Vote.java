package com.example.btcposition.domain.vote.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@EqualsAndHashCode
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private VoteType value;
    private int count;

    @Column(name = "vote_date")
    private LocalDate voteDate;

    public Vote() {

    }

    public Vote(VoteType value, int count) {
        this.value = value;
        this.count = count;
    }

    @PrePersist
    private void perPersist() {
        this.voteDate = LocalDate.now();
    }


}
