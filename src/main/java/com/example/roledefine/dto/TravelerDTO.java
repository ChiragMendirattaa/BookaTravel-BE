package com.example.roledefine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TravelerDTO {
    private String type;
    private String title;
    private String firstName;
    private String lastName;

    @JsonProperty("isMainTraveler")
    private boolean isMainTraveler;
}