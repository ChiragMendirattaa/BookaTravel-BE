package com.example.roledefine.dto.hoteldto.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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