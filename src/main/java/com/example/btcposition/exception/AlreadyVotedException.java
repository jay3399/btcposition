package com.example.btcposition.exception;

public class AlreadyVotedException extends RuntimeException {


    private static final String MESSAGE = "이미 투표한 사용자입니다.";

    public AlreadyVotedException() {
        super(MESSAGE);
    }


}


