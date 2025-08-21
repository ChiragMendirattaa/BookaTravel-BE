package com.example.roledefine.dto.flight.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaxDetails {
    @NotNull
    private Adult adult;

    @NotNull
    private Child child;

    @NotNull
    private Infant infant;
}

//public class PaxDetails {
//    @JsonProperty("first_name")
//    private String firstName;
//
//    @JsonProperty("last_name")
//    private String lastName;
//
//    @JsonProperty("age")
//    private int age;
//
//    @JsonProperty("gender")
//    private String gender;
//
//    @JsonProperty("passport_number")
//    private String passportNumber;
//
//    @JsonProperty("nationality")
//    private String nationality;
//}