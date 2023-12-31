package com.example.btcposition.infrastructure.api;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import reactor.core.publisher.Mono;

public interface BtcPriceProvider {

    Mono<BigDecimal> getBtcPrice() throws URISyntaxException;

    BigDecimal getBtcPriceSync() throws URISyntaxException;



}
