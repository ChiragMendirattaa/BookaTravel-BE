package com.example.roledefine.service;

import com.example.roledefine.dto.hoteldto.request.HotelSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class HotelSearchService {

    private final WebClient webClient;

    @Value("${travelnext.api.user_id}")
    private String apiUserId;

    @Value("${travelnext.api.password}")
    private String apiPassword;

    @Value("${travelnext.api.access}")
    private String apiAccess;

    @Value("${travelnext.api.ip_address}")
    private String apiIpAddress;

    public HotelSearchService(WebClient travelNextWebClient) {
        this.webClient = travelNextWebClient;
    }

    public Mono<String> searchHotels(HotelSearchRequest request) {
        request.setUser_id(apiUserId);
        request.setUser_password(apiPassword);
        request.setAccess(apiAccess);
        request.setIp_address(apiIpAddress);
        return fetchPage(request);
    }

    public Mono<String> getMoreResults(String sessionId, String nextToken) {
        log.info("Fetching next page with dedicated pagination URL for session: {}", sessionId);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel_trawexv6/moreResultsPagination")
                        .queryParam("sessionId", sessionId)
                        .queryParam("nextToken", nextToken)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(raw -> log.info("Fetched next page of hotel results."));
    }


    private Mono<String> fetchPage(HotelSearchRequest request) {
        return webClient.post()
                .uri("/hotel_trawexv6/hotel_search")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(raw -> log.info("Fetched a page of hotel results."));
    }

    public Mono<String> getHotelDetails(String hotelId, String sessionId, String tokenId, String productId) {
        log.info("Fetching details for hotelId: {}", hotelId);
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/hotel_trawexv6/hotelDetails")
                        .queryParam("sessionId", sessionId)
                        .queryParam("hotelId", hotelId)
                        .queryParam("tokenId", tokenId)
                        .queryParam("productId", productId)
                        .build())
                .header("Content-Type", "application/json")
                .bodyValue("")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(raw -> log.info("Fetched hotel content for hotelId: {}", hotelId));
    }
}