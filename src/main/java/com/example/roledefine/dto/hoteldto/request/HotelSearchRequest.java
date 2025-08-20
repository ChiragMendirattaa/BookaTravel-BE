package com.example.roledefine.dto.hoteldto.request;

import com.example.roledefine.dto.OccupancyDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HotelSearchRequest extends BaseRequest {

    private String requiredCurrency;
    private String nationality;

    @JsonProperty("checkin")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String checkin;

    @JsonProperty("checkout")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String checkout;

    private Integer resultsPerPage;
    private String city_name;
    private String country_name;
    private Integer maxResult;

    private List<OccupancyDTO> occupancy;

    private String sessionId;
    private String nextToken;
}
