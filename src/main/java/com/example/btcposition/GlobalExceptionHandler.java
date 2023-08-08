package com.example.btcposition;

import com.example.btcposition.domain.ErrorResponse;
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
        log.error("Already voted exception:", e);
        ErrorResponse response = ErrorResponse.create(e, HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        // badRequest -> 400에러 , 클라이언트 요청자체가 틀린것 <-> 403 요청은 올바르지만 권한이슈 . 403이 적절하다
    }

    @ExceptionHandler(RedisCommunicationException.class)
    public ResponseEntity<ErrorResponse> handleRedisException(RedisCommunicationException e) {
        log.error("redis error", e);
        ErrorResponse response = ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity<ErrorResponse> handleJWTException(JWTException e) {
        log.error("jwt error" , e);
        ErrorResponse response = ErrorResponse.create(e, HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ScheduledTaskException.class)
    public ResponseEntity<ErrorResponse> handleScheduledTaskException(ScheduledTaskException e) {

        log.error("scheduled error", e);
        ErrorResponse response = ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);


    }
 }

