package com.example.roledefine.dto.flight.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Infant {
    @NotNull
    private List<String> title;
    @NotNull
    private List<String> firstName;
    @NotNull
    private List<String> lastName;
    @NotNull
    private List<String> dob;
    @NotNull
    private List<String> nationality;

    private List<String> passportNo;
    private List<String> passportIssueCountry;
    private List<String> passportExpiryDate;
    private List<String> passportIssueDate;

    private List<String> frequentFlyrNum;
}