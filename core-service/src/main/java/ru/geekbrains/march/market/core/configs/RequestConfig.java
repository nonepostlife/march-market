package ru.geekbrains.march.market.core.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RequestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
