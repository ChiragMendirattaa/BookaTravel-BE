package com.example.roledefine.dto.hoteldto.request;

import com.example.roledefine.dto.PaxDetailsDTO;
import com.example.roledefine.dto.TravelerDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HotelBookingRequestDTO extends HotelSearchRequest {

    private String tokenId;
    private String rateBasisId;
    private String productId;
    private String customerEmail;
    private String customerPhone;
    private String bookingNote;
    private List<TravelerDTO> travelers;
    private List<PaxDetailsDTO> paxDetails;

    @JsonProperty("clientRef")
    private String clientRef;
}