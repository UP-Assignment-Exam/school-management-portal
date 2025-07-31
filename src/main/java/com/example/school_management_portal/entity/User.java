package com.example.school_management_portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    private String password; // stored hashed (BCrypt)

    private String role; // e.g., ADMIN, TEACHER, STUDENT

    private boolean enabled = true;
}

