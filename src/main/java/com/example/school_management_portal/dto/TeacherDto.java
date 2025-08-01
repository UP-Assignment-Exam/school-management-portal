package com.example.school_management_portal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeacherDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean isActive;
    private String imageUrl;
}