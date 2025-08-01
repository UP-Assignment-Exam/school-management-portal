package com.example.school_management_portal.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttendanceDto {

    @NotNull
    private String date; // Use String or LocalDate depending on mapping setup

    @NotBlank
    private String status; // e.g., PRESENT, ABSENT, LATE

    private String remarks;

    @NotNull
    private Long studentId;

    @NotNull
    private Long classId;
}