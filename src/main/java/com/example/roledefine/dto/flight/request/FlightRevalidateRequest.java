package com.example.roledefine.dto.flight.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRevalidateRequest {

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("fare_source_code")
    private String fareSourceCode;
}