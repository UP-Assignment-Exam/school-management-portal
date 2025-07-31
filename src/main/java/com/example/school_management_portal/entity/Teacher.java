package com.example.school_management_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean isActive;

    private String imageUrl; // Store the image path or URL

    @OneToMany(mappedBy = "teacher")
    private List<ClassEntity> classes;
}
