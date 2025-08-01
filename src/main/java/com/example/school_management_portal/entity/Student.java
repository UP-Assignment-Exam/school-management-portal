package com.example.school_management_portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;


    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ClassEntity> classes = new HashSet<>();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Attendance> attendanceRecords;
}
