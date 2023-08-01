package com.example.btcposition;


import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private String username;
  private boolean voted;


  public JwtResponse(String token, String username, boolean voted) {
    this.token = token;
    this.username = username;
    this.voted = voted;
  }
}
