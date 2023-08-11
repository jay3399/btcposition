package com.example.btcposition.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {


    private String message;
    private int status;

    public static ErrorResponse create(Exception e, int status) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setStatus(status);
        return response;
    }


}
