package com.example.school_management_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    private LocalDate date;
    private String status; // PRESENT, ABSENT, LATE, etc.
    private String remarks;

    @ManyToOne
    private Student student;

    @ManyToOne
    private ClassEntity classEntity;
}
