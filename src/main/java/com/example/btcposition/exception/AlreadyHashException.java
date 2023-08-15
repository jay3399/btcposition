package com.example.btcposition.exception;

public class AlreadyHashException extends RuntimeException {

    private static final String MESSAGE = "이미 해시값이 존재합니다";


    public AlreadyHashException() {
        super(MESSAGE);
    }


}
