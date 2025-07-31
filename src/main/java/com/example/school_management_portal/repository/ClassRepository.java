package com.example.school_management_portal.repository;

import com.example.school_management_portal.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    // Custom query methods if needed
}