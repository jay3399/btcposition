package com.example.btcposition.exception;

public class RedisCommunicationException extends RuntimeException {


    private static final String MESSAGE = "레디스와의 통신중 오류가 발생했습니다";
    public RedisCommunicationException(Throwable cause) {
        super(MESSAGE, cause);
    }


}
