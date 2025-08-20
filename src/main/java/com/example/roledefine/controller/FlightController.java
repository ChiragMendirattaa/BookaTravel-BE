package com.example.roledefine.controller;

import com.example.roledefine.dto.flight.request.FlightRevalidateRequest;
import com.example.roledefine.dto.flight.request.FlightSearchRequest;
import com.example.roledefine.exception.FlightSearchException;
import com.example.roledefine.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightSearchService;

    @PostMapping("/search")
    public ResponseEntity<?> searchFlights(@RequestBody FlightSearchRequest request) {
        try {
            String rawResponse = flightSearchService.searchFlightsRaw(request);
            return ResponseEntity.ok(rawResponse);
        } catch (FlightSearchException e) {
            log.warn("Flight search failed: {}", e.getMessage());
            return ResponseEntity.status(502).body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during flight search", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @PostMapping("/revalidate")
    public ResponseEntity<?> revalidateFlight(@RequestBody FlightRevalidateRequest flightRevalidateRequest) {
        try {
            String rawResponse = flightSearchService.revalidateFlight(flightRevalidateRequest);
            return ResponseEntity.ok(rawResponse);
        } catch (FlightSearchException e) {
            log.warn("Flight revalidation failed: {}", e.getMessage());
            return ResponseEntity.status(502).body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during flight revalidation", e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}