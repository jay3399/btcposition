package com.example.btcposition.domain.vote.model;

public enum VoteType {

    LONG,
    SHORT;

    public static VoteType fromString(String value) {
        try {
            return VoteType.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid vote type: " + value);
        }
    }


}
