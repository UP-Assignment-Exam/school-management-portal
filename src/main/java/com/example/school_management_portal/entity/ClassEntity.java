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
public class ClassEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxStudents;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "student_class",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    @OneToMany(mappedBy = "classEntity")
    private List<Attendance> attendanceRecords;

    @OneToMany(mappedBy = "classEntity")
    private List<Schedule> schedules;
}
