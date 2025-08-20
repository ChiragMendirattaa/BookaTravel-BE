package com.example.roledefine.controller;

import com.example.roledefine.dto.hoteldto.request.HotelSearchRequest;
import com.example.roledefine.service.HotelSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelSearchController {

    private final HotelSearchService hotelSearchService;

    @PostMapping("/search")
    public Flux<String> searchHotels(@RequestBody HotelSearchRequest request) {
        return hotelSearchService.searchHotels(request);
    }
}
