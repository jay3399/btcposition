package com.example.btcposition;

import com.example.btcposition.domain.ErrorResponse;
import com.example.btcposition.exception.AlreadyHashException;
import com.example.btcposition.exception.AlreadyVotedException;
import com.example.btcposition.exception.JWTException;
import com.example.btcposition.exception.RedisCommunicationException;
import com.example.btcposition.exception.ScheduledTaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

// 예외처리 중앙화

    @ExceptionHandler(AlreadyVotedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyVotedException(AlreadyVotedException e) {

        return handleException(e, HttpStatus.FORBIDDEN, e.getMessage());
        // badRequest -> 400에러 , 클라이언트 요청자체가 틀린것 <-> 403 요청은 올바르지만 권한이슈 . 403이 적절하다
    }

    @ExceptionHandler(RedisCommunicationException.class)
    public ResponseEntity<ErrorResponse> handleRedisException(RedisCommunicationException e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity<ErrorResponse> handleJWTException(JWTException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ScheduledTaskException.class)
    public ResponseEntity<ErrorResponse> handleScheduledTaskException(ScheduledTaskException e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(AlreadyHashException.class)
    public ResponseEntity<ErrorResponse> handleHashException(AlreadyHashException e) {
        return handleException(e, HttpStatus.FORBIDDEN, e.getMessage());
    }

    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpStatus status,
            String logMessage) {

        log.error(logMessage, e);
        ErrorResponse response = ErrorResponse.create(e, status.value());
        return ResponseEntity.status(status).body(response);
    }


}

//        log.error("Already voted exception:", e);
//        ErrorResponse response = ErrorResponse.create(e, HttpStatus.FORBIDDEN.value());
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

