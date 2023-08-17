package com.example.btcposition.service;


import com.example.btcposition.dto.BtcPriceResponse;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BtcPriceService implements BtcPriceProvider {

    private final WebClient webClient = WebClient.create();


    public Mono<BigDecimal> getBtcPrice() throws URISyntaxException {
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd";

        URI uri = new URI(url);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(BtcPriceResponse.class)
                .map(btcPriceResponse -> new BigDecimal(btcPriceResponse.getBitcoin().get("usd").toString()));

    }


}
