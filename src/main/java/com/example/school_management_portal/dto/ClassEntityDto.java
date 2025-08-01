package com.example.school_management_portal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassEntityDto {
    private String name;
    private String description;
    private LocalDate startDate;   // You can change to LocalDate if preferred
    private LocalDate endDate;
    private Integer maxStudents;
    private TeacherRefDto teacher;
    private List<Long> studentIds = new ArrayList<>();
}