package com.example.roledefine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient flightWebClient() {
        return WebClient.builder()
                .baseUrl("https://travelnext.works/api/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient travelNextWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://travelnext.works/api")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}