package com.example.school_management_portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Teacher extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;


    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Size(max = 255, message = "Image URL must not exceed 255 characters")
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClassEntity> classes = new ArrayList<>();
}
