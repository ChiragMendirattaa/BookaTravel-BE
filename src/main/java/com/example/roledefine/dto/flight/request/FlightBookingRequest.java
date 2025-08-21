package com.example.roledefine.dto.flight.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightBookingRequest {
    private FlightBookingInfo flightBookingInfo;
    private PaxInfo paxInfo;
}