package com.example.school_management_portal.repository;

import com.example.school_management_portal.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // Custom query methods if needed
}