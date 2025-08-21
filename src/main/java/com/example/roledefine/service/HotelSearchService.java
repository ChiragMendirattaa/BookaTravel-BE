package com.example.roledefine.service;

import com.example.roledefine.dto.hoteldto.request.*;
import com.example.roledefine.entity.HotelTransaction;
import com.example.roledefine.repository.HotelTransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelSearchService {

    private final WebClient webClient;
    private final CredentialProvider credentialProvider;
    private final HotelTransactionRepository hotelTransactionRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<String> searchHotels(HotelSearchRequest request) {
        credentialProvider.populate(request);
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
        credentialProvider.populate(request);
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
        credentialProvider.populate(request);
        return webClient.post()
                .uri("/hotel_trawexv6/get_rate_rules")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(raw -> log.info("Checked rate rules for productId: {}", request.getProductId()));
    }

    public Mono<String> initiateBooking(HotelBookingRequestDTO request) {
        log.info("Initiating booking for productId: {}", request.getProductId());

        HotelTransaction transaction = new HotelTransaction();
        transaction.setSessionId(request.getSessionId());
        transaction.setProductId(request.getProductId());
        transaction.setTokenId(request.getTokenId());
        transaction.setRateBasisId(request.getRateBasisId());
        transaction.setBookingStatus("INITIALIZED");

        try {
            String guestData = objectMapper.writeValueAsString(request.getGuests());
            transaction.setGuestData(guestData);
        } catch (Exception e) {
            log.error("Error serializing guest data", e);
            return Mono.error(new RuntimeException("Could not process guest information."));
        }

        hotelTransactionRepository.save(transaction);
        log.info("Saved initial transaction with ID: {}", transaction.getId());

        String fakeClientSecret = "pi_" + transaction.getId() + "_secret_mockedfortesting";
        transaction.setPaymentIntentId("pi_" + transaction.getId());
        hotelTransactionRepository.save(transaction);

        return Mono.just("{\"clientSecret\": \"" + fakeClientSecret + "\"}");
    }
}