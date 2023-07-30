package com.example.btcposition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class mainController {


  @PostMapping("/position/vote/{value}")
  @ResponseBody
  public boolean getVote(@PathVariable String value) {

    System.out.println("value = " + value);

    return true;
  }


}
