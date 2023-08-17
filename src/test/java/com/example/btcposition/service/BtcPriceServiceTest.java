//package com.example.btcposition.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import com.example.btcposition.dto.BtcPriceResponse;
//import java.math.BigDecimal;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.Map;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//
//@SpringBootTest
//class BtcPriceServiceTest {
//
//    @MockBean
//    private WebClient webClient;
//
//
//    @Mock
//    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
//
//    @Mock
//    private WebClient.RequestHeadersSpec requestHeadersSpec;
//
//    @Mock
//    private WebClient.ResponseSpec responseSpec;
//
//    @InjectMocks
//    private BtcPriceService bitcoinPriceService;
//
//
//    @Test
//    public void testGetBitcoinPrice() throws URISyntaxException {
//        // Arrange
//        when(webClient.get()).thenReturn(requestHeadersUriSpec);
//        when(requestHeadersUriSpec.uri(any(URI.class))).thenReturn(requestHeadersSpec);
//        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
//        when(responseSpec.bodyToMono(BtcPriceResponse.class)).thenReturn(
//                Mono.just(new BtcPriceResponse(
//                        Map.of("bitcoin", Map.of("usd", 29400)))));
//
//        // Act
//        Mono<BigDecimal> result = bitcoinPriceService.getBtcPrice();
//
//        // Assert
//        StepVerifier.create(result)
//                .expectNext(new BigDecimal("29400"))
//                .verifyComplete();
//    }
//
//
//}