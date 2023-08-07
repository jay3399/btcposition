package com.example.btcposition;


import lombok.Getter;

@Getter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String username;


    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
