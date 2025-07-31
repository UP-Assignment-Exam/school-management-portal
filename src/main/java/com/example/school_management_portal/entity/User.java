package com.example.school_management_portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean enabled = true;

    public User(String username, String password, Role role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Role enum
    public enum Role {
        ADMIN,
        TEACHER,
        STUDENT
    }
}

