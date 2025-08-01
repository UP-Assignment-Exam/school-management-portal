package com.example.school_management_portal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentDto {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;  // or LocalDate with Jackson configured
    private String gender;
    private String address;
    private LocalDate enrollmentDate;
    private Boolean isActive;
    private String imageUrl;
}
