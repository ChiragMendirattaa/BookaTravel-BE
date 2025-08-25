package com.example.roledefine.dto.flight.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaxInfo {
    private String clientRef;
    private String postCode;

    @NotNull
    @Email
    private String customerEmail;

    @NotNull
    private String customerPhone;

    private String bookingNote;

    @NotNull
    private List<PaxDetails> paxDetails;
}

//public class PaxInfo {
//    private List<Adult> adults;
//    private List<Child> children;
//}