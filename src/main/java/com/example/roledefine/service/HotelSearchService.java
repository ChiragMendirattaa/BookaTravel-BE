package com.example.roledefine.service;

import com.example.roledefine.dto.hoteldto.request.CheckRoomRatesRequestDTO;
import com.example.roledefine.dto.hoteldto.request.HotelSearchRequest;
import com.example.roledefine.dto.hoteldto.request.RoomRatesRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor // <-- ADD this annotation for cleaner dependency injection
public class HotelSearchService {

    private final WebClient webClient;
    private final CredentialProvider credentialProvider; // <-- INJECT the new provider

    // The @Value annotations have been REMOVED from this file.

    public Mono<String> searchHotels(HotelSearchRequest request) {
        credentialProvider.populate(request); // <-- USE the provider
        return fetchPage(request);
    }

    public Mono<String> getMoreResults(String sessionId, String nextToken) {
        log.info("Fetching next page for session: {}", sessionId);
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

    public Mono<String> getRoomRates(RoomRatesRequestDTO request) {
        log.info("Fetching room rates for hotelId: {}", request.getHotelId());
        credentialProvider.populate(request); // <-- USE the provider
        return webClient.post()
                .uri("/hotel_trawexv6/get_room_rates")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(raw -> log.info("Fetched room rates for hotelId: {}", request.getHotelId()));
    }

    public Mono<String> checkRoomRates(CheckRoomRatesRequestDTO request) {
        log.info("Checking rate rules for productId: {}", request.getProductId());
        credentialProvider.populate(request); // <-- USE the provider
        return webClient.post()
                .uri("/hotel_trawexv6/get_rate_rules")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(raw -> log.info("Checked rate rules for productId: {}", request.getProductId()));
    }

    // The private helper method has been MOVED to the CredentialProvider
}