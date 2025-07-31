package com.example.school_management_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private LocalDate enrollmentDate;
    private boolean isActive;

    private String imageUrl; // Store the image path or URL

    @ManyToMany(mappedBy = "students")
    private Set<ClassEntity> classes;

    @OneToMany(mappedBy = "student")
    private List<Attendance> attendanceRecords;
}
