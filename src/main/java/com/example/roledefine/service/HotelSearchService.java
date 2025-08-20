package com.example.roledefine.service;

import com.example.roledefine.dto.hoteldto.request.HotelSearchRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class HotelSearchService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HotelSearchService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://travelnext.works/api")
                .build();
    }

    /**
     * Fetch one page of hotels.
     */
    public Mono<String> fetchPage(HotelSearchRequest request) {
        return webClient.post()
                .uri("/hotel_trawexv6/hotel_search")
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(raw -> log.info("Fetched page response: {}", raw));
    }

    /**
     * Fetch all hotel pages until `moreResults = false`.
     */
    public Flux<String> fetchAllHotels(HotelSearchRequest request) {
        return Flux.defer(() -> fetchPage(request))
                .expand(response -> {
                    try {
                        JsonNode root = objectMapper.readTree(response);
                        JsonNode status = root.path("status");

                        boolean moreResults = status.path("moreResults").asBoolean(false);
                        String nextToken = status.path("nextToken").asText(null);

                        if (moreResults && nextToken != null && !nextToken.isEmpty()) {
                            log.info("More results found, fetching next page with token: {}", nextToken);

                            HotelSearchRequest nextReq = request.toBuilder()
                                    .nextToken(nextToken)
                                    .build();

                            return fetchPage(nextReq);
                        } else {
                            return Mono.empty();
                        }
                    } catch (Exception e) {
                        log.error("Error parsing hotel search response", e);
                        return Mono.empty();
                    }
                });
    }

    /**
     * Alias for controller – so it can call `searchHotels` directly.
     */
    public Flux<String> searchHotels(HotelSearchRequest request) {
        return fetchAllHotels(request);
    }
}
