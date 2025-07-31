package com.example.school_management_portal.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String status; // e.g., PRESENT, ABSENT, LATE

    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_entity_id")
    @JsonBackReference("class-attendance")
    private ClassEntity classEntity;
}
