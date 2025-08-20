package com.example.roledefine.service;

import com.example.roledefine.dto.flight.request.FlightSearchRequest;
import com.example.roledefine.exception.FlightSearchException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightService {

    private static final String FLIGHT_SEARCH_PATH = "aeroVE5/availability";
    private final WebClient flightWebClient;

    public String searchFlightsRaw(FlightSearchRequest request) {
        try {
            String rawJson = flightWebClient.post()
                    .uri(FLIGHT_SEARCH_PATH)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (rawJson == null || rawJson.isBlank()) {
                throw new FlightSearchException("Empty response from flight API");
            }

            log.info("Raw flight search response: {}", rawJson);
            return rawJson;

        } catch (Exception e) {
            log.error("Failed to fetch raw flight search response", e);
            throw new FlightSearchException("Unable to retrieve flight data");
        }
    }
}