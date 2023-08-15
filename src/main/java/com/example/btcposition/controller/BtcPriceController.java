package com.example.btcposition.controller;

import com.example.btcposition.service.BtcPriceProvider;
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

    private final BtcPriceProvider btcPriceService;

    private final BtcPriceProvider btcPriceProvider = new BtcPriceService();


    @GetMapping("/api/btc-price")
    public Mono<BigDecimal> getBtcPrice() throws URISyntaxException {

        Mono<BigDecimal> btcPrice = btcPriceService.getBtcPrice();

        //OCP
        //다양한 출처로부터 가져올수있다 , controller getBrcPrice가 어떻게 작동하는지 알필요가없고
        //단지 , 비트코인의 가격을 가져온다는것만 알면되고 , 그것을 요청하고 결과만을 받는다 .
        //세부 구현동작은 몰라도된다 , 캡슐화 , 객체가 자율적으로.
        //세부 구현동작을 알필요가 없으니 , 구현체가 바뀌어도 상관이없다. 코드변경이 필요가없다 .
        //구현체는 실행하는 시점에서 결정이된다.

        //하지만 스프링프레임워크 전에 문제점은 위와같이 , new BtcPriceService 처럼 구체적인 코드의 변경이필요하기떄문에
        //확정에는 열려있지만 , 수정또한 열려있다
        //스프링프레임워크에서는 @Service 같은 어노테이션으로 스프링빈을 주입받아 런타임시점에서 완성을 시켜준다 .






        return btcPrice;

    }



}
