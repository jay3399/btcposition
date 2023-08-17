package com.example.btcposition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;

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
