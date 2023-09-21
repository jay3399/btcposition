package com.example.btcposition.application.ui.response;


import lombok.Getter;

@Getter
public class JwtResponse {

    private final String token;
    private final String username;


    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
