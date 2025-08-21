package com.example.roledefine.dto.flight.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraService {
    @NotNull
    private String serviceId;
    @NotNull
    private String quantity;
    @NotNull
    private String segment;
}