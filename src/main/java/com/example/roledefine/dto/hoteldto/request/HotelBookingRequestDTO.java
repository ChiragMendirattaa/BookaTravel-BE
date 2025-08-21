package com.example.roledefine.dto.hoteldto.request;

import com.example.roledefine.dto.GuestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HotelBookingRequestDTO extends BaseRequest {

    private String sessionId;
    private String tokenId;
    private String productId;
    private String rateBasisId;

    private List<GuestDTO> guests;

    private String customerEmail;
    private String customerPhone;
}