package com.example.roledefine.dto.hoteldto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookingDetailsRequestDTO extends BaseRequest {

    private String referenceNum;
    private String supplierConfirmationNum;
}