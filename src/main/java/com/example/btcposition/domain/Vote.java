package com.example.btcposition.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
public class Vote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String value;
  private int count;

  public Vote(String value, int count) {
    this.value = value;
    this.count = count;
  }

  public static String toJSON(Vote vote) {
    return "{\"value\": \"" + vote.getValue() + "\", \"count\": " + vote.getCount() + "}";
  }


}