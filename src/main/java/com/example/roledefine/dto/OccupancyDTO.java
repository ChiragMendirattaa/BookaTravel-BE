package com.example.roledefine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyDTO {
    private Integer room_no;
    private Integer adult;
    private Integer child;
    private List<Integer> child_age;
}
