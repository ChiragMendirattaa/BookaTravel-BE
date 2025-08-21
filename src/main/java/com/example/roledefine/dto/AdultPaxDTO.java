package com.example.roledefine.dto;

import lombok.Data;
import java.util.List;

@Data
public class AdultPaxDTO {
    private List<String> title;
    private List<String> firstName;
    private List<String> lastName;
}