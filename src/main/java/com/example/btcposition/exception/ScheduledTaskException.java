package com.example.btcposition.exception;

public class ScheduledTaskException extends RuntimeException {


    private static final String MESSAGE = "스케줄링 작업중 오류가 발생했습니다";

    public ScheduledTaskException(Throwable cause) {
        super(MESSAGE, cause);
    }


}
