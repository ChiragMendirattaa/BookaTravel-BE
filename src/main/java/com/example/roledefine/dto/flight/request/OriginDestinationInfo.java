package com.example.roledefine.dto.flight.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginDestinationInfo {
    private String origin;
    private String destination;

    @JsonProperty("departureDate")
    private String departureDate; // Format: YYYY-MM-DD

    @JsonProperty("airportOriginCode")
    private String airportOriginCode;

    @JsonProperty("airportDestinationCode")
    private String airportDestinationCode;
}
