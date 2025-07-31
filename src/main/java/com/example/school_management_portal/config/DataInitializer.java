package com.example.school_management_portal.config;

import com.example.school_management_portal.entity.User;
import com.example.school_management_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (!userService.existsByUsername("admin")) {
            userService.createUser("admin", "admin123", User.Role.ADMIN);
            System.out.println("Default admin user created: username=admin, password=admin123");
        }

        // Create a sample teacher
        if (!userService.existsByUsername("teacher1")) {
            userService.createUser("teacher1", "teacher123", User.Role.TEACHER);
            System.out.println("Sample teacher created: username=teacher1, password=teacher123");
        }

        // Create a sample student
        if (!userService.existsByUsername("student1")) {
            userService.createUser("student1", "student123", User.Role.STUDENT);
            System.out.println("Sample student created: username=student1, password=student123");
        }
    }
}