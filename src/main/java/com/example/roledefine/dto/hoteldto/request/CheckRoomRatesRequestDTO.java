package com.example.roledefine.dto.hoteldto.request;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CheckRoomRatesRequestDTO extends BaseRequest {

    private String sessionId;
    private String tokenId;

    private String productId;

    private String rateBasisId;
}