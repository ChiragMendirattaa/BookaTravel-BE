package com.example.roledefine.service;

import  com.example.roledefine.dto.hoteldto.request.*;
import com.example.roledefine.entity.AnonymousUser;
import com.example.roledefine.entity.HotelTransaction;
import com.example.roledefine.entity.User;
import com.example.roledefine.repository.AnonymousUserRepository;
import com.example.roledefine.repository.HotelTransactionRepository;
import com.example.roledefine.repository.UserRepository;
import com.example.roledefine.util.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelSearchService {

    private final WebClient webClient;
    private final CredentialProvider credentialProvider;
    private final HotelTransactionRepository hotelTransactionRepository;
    private final ObjectMapper objectMapper;

    private final AnonymousUserRepository anonymousUserRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public Mono<String> searchHotels(HotelSearchRequest request) { credentialProvider.populate(request); return fetchPage(request); }
    public Mono<String> getMoreResults(String sessionId, String nextToken) { log.info("Fetching next page for session: {}", sessionId); return webClient.get().uri(uriBuilder -> uriBuilder.path("/hotel_trawexv6/moreResultsPagination").queryParam("sessionId", sessionId).queryParam("nextToken", nextToken).build()).retrieve().bodyToMono(String.class).doOnNext(raw -> log.info("Fetched next page of hotel results.")); }
    private Mono<String> fetchPage(HotelSearchRequest request) { return webClient.post().uri("/hotel_trawexv6/hotel_search").header("Content-Type", "application/json").bodyValue(request).retrieve().bodyToMono(String.class).doOnNext(raw -> log.info("Fetched a page of hotel results.")); }
    public Mono<String> getHotelDetails(String hotelId, String sessionId, String tokenId, String productId) { log.info("Fetching details for hotelId: {}", hotelId); return webClient.post().uri(uriBuilder -> uriBuilder.path("/hotel_trawexv6/hotelDetails").queryParam("sessionId", sessionId).queryParam("hotelId", hotelId).queryParam("tokenId", tokenId).queryParam("productId", productId).build()).header("Content-Type", "application/json").bodyValue("").retrieve().bodyToMono(String.class).doOnNext(raw -> log.info("Fetched hotel content for hotelId: {}", hotelId)); }
    public Mono<String> getRoomRates(RoomRatesRequestDTO request) { log.info("Fetching room rates for hotelId: {}", request.getHotelId()); credentialProvider.populate(request); return webClient.post().uri("/hotel_trawexv6/get_room_rates").header("Content-Type", "application/json").bodyValue(request).retrieve().bodyToMono(String.class).doOnNext(raw -> log.info("Fetched room rates for hotelId: {}", request.getHotelId())); }
    public Mono<String> checkRoomRates(CheckRoomRatesRequestDTO request) { log.info("Checking rate rules for productId: {}", request.getProductId()); credentialProvider.populate(request); return webClient.post().uri("/hotel_trawexv6/get_rate_rules").header("Content-Type", "application/json").bodyValue(request).retrieve().bodyToMono(String.class).doOnNext(raw -> log.info("Checked rate rules for productId: {}", request.getProductId())); }
    public Mono<String> confirmBooking(ConfirmBookingRequestDTO confirmRequest) { String paymentIntentId = confirmRequest.getPaymentIntentId(); log.info("Confirming booking for paymentIntentId: {}", paymentIntentId); HotelTransaction transaction = hotelTransactionRepository.findByPaymentIntentId(paymentIntentId).orElseThrow(() -> new RuntimeException("Transaction not found for payment ID: " + paymentIntentId)); log.info("Payment verification successful (simulated)."); transaction.setBookingStatus("PENDING_CONFIRMATION"); hotelTransactionRepository.save(transaction); return Mono.fromCallable(() -> { HotelBookingRequestDTO trawexRequest = objectMapper.readValue(transaction.getBookingRequestData(), HotelBookingRequestDTO.class); trawexRequest.setClientRef(UUID.randomUUID().toString()); credentialProvider.populate(trawexRequest); return trawexRequest; }).flatMap(trawexRequest -> webClient.post().uri("/hotel_trawexv6/hotel_book").bodyValue(trawexRequest).retrieve().bodyToMono(String.class)).flatMap(trawexResponse -> { try { JsonNode responseNode = objectMapper.readTree(trawexResponse); JsonNode statusNode = responseNode.path("status"); if ("CONFIRMED".equalsIgnoreCase(statusNode.asText())) { String refNum = responseNode.path("referenceNum").asText(); String confNum = responseNode.path("supplierConfirmationNum").asText(); transaction.setBookingStatus("CONFIRMED"); transaction.setReferenceNum(refNum); transaction.setSupplierConfirmationNum(confNum); hotelTransactionRepository.save(transaction); return Mono.just(trawexResponse); } else { log.error("Trawex booking failed. Full response: {}", trawexResponse); transaction.setBookingStatus("FAILED"); hotelTransactionRepository.save(transaction); return Mono.error(new RuntimeException("Booking failed at supplier. Full response: " + trawexResponse)); } } catch (Exception e) { return Mono.error(new RuntimeException("Error parsing Trawex confirmation response.", e)); } }); }
    public Mono<String> getBookingDetails(BookingDetailsRequestDTO request) { log.info("Fetching booking details for referenceNum: {}", request.getReferenceNum()); credentialProvider.populate(request); return webClient.post().uri("/hotel_trawexv6/bookingDetails").header("Content-Type", "application/json").bodyValue(request).retrieve().bodyToMono(String.class).doOnNext(raw -> log.info("Fetched booking details.")); }

    public Mono<String> initiateBooking(HotelBookingRequestDTO request, String authToken, UUID anonymousId) {
        log.info("Initiating booking for hotel: {}", request.getCity_name());

        HotelTransaction transaction = new HotelTransaction();

        if (authToken != null && authToken.startsWith("Bearer ")) {
            String jwt = authToken.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found for token."));
            transaction.setUser(user);
            log.info("Booking initiated by registered user: {}", username);
        }
        else if (anonymousId != null) {
            AnonymousUser anonymousUser = anonymousUserRepository.findById(anonymousId)
                    .orElseThrow(() -> new RuntimeException("Anonymous user session not found."));
            transaction.setAnonymousUser(anonymousUser);
            log.info("Booking initiated by anonymous user: {}", anonymousId);
        }
        else {
            return Mono.error(new RuntimeException("User context (auth token or anonymous ID) is required to initiate a booking."));
        }

        transaction.setBookingStatus("INITIALIZED");
        try {
            String bookingData = objectMapper.writeValueAsString(request);
            transaction.setBookingRequestData(bookingData);
        } catch (Exception e) {
            log.error("Error serializing booking request data", e);
            return Mono.error(new RuntimeException("Could not process booking information."));
        }

        hotelTransactionRepository.save(transaction);
        log.info("Saved initial transaction with ID: {}", transaction.getId());

        String fakeClientSecret = "pi_" + transaction.getId() + "_secret_mockedfortesting";
        transaction.setPaymentIntentId("pi_" + transaction.getId());
        hotelTransactionRepository.save(transaction);

        return Mono.just("{\"clientSecret\": \"" + fakeClientSecret + "\"}");
    }
}