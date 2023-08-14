package com.example.btcposition.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.btcposition.domain.BtcPriceResponse;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest
class BtcPriceServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private BtcPriceService bitcoinPriceService;

    @Test
    public void testGetBitcoinPrice() throws URISyntaxException {
        // Arrange
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd"))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(BtcPriceResponse.class)).thenReturn(Mono.just(new BtcPriceResponse(
                Map.of("usd", 45000.0))));

        // Act
        Mono<BigDecimal> result = bitcoinPriceService.getBtcPrice();

        // Assert
        StepVerifier.create(result)
                .expectNext(new BigDecimal("45000.0"))
                .verifyComplete();
    }


}