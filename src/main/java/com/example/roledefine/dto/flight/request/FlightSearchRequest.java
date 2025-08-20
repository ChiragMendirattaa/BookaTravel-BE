package com.example.roledefine.dto.flight.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchRequest {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_password")
    private String userPassword;

    private String access;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("requiredCurrency")
    private String requiredCurrency;

    @JsonProperty("journeyType")
    private JourneyType journeyType = JourneyType.OneWay;

    @JsonProperty("OriginDestinationInfo")
    private List<OriginDestinationInfo> originDestinationInfo;

    @JsonProperty("class")
    private String travelClass;

    private int adults;
    private int childs;
    private int infants;

    @JsonProperty("directFlight")
    private int directFlight;
}