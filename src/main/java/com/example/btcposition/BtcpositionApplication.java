package com.example.btcposition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;


// 비트코인의 상승 / 하락 결과 테이블에 저장 o

// 이메일 구독서비스 -> rabbitmq 사용 비동기처리 x



@SpringBootApplication
@EnableScheduling
public class BtcpositionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BtcpositionApplication.class, args);

    }


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource m = new ReloadableResourceBundleMessageSource();

        m.setBasename("classpath:messages");
        m.setDefaultEncoding("UTF-8");
        return m;
    }

}
