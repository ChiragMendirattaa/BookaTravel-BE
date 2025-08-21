package com.example.roledefine.dto.hoteldto.request;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoomRatesRequestDTO extends BaseRequest {

    private String sessionId;
    private String hotelId;
    private String tokenId;
    private String productId;
}