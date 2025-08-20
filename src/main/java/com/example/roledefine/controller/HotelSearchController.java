package com.example.roledefine.controller;

import com.example.roledefine.dto.hoteldto.request.HotelSearchRequest;
import com.example.roledefine.service.HotelSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelSearchController {

    private final HotelSearchService hotelSearchService;

    @PostMapping("/search")
    public Mono<String> searchHotels(@RequestBody HotelSearchRequest request) {
        return hotelSearchService.searchHotels(request);
    }

    @GetMapping("/more-results")
    public Mono<String> getMoreResults(@RequestParam String sessionId, @RequestParam String nextToken) {
        return hotelSearchService.getMoreResults(sessionId, nextToken);
    }

    @GetMapping("/{hotelId}")
    public Mono<String> getHotelDetails(@PathVariable String hotelId,
                                        @RequestParam String sessionId,
                                        @RequestParam String tokenId,
                                        @RequestParam String productId) {
        return hotelSearchService.getHotelDetails(hotelId, sessionId, tokenId, productId);
    }
}