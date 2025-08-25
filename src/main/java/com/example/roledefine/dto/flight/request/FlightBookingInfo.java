package com.example.roledefine.dto.flight.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightBookingInfo {
    @JsonProperty("flight_session_id")
    private String flightSessionId;

    @JsonProperty("fare_source_code")
    private String fareSourceCode;

    @JsonProperty("IsPassportMandatory")
    private boolean isPassportMandatory;

    @JsonProperty("fareType")
    private String fareType;

    @JsonProperty("areaCode")
    private String areaCode;

    @JsonProperty("countryCode")
    private String countryCode;
}