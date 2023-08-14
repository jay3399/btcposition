package com.example.btcposition.controller;

import com.example.btcposition.service.BtcPriceService;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BtcPriceController {

    private final BtcPriceService btcPriceService;


    @GetMapping("/api/btc-price")
    public Mono<BigDecimal> getBtcPrice() throws URISyntaxException {

        Mono<BigDecimal> btcPrice = btcPriceService.getBtcPrice();

        return btcPrice;

    }



}
