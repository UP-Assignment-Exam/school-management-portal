package com.example.school_management_portal.repository;

import com.example.school_management_portal.entity.Teacher;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;


@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE t.isDeleted = false")
    List<Teacher> findAllActive();

    @Query("SELECT t FROM Teacher t WHERE t.id = :id AND t.isDeleted = false")
    Optional<Teacher> findByIdAndNotDeleted(Long id);

    @Query("SELECT t FROM Teacher t WHERE t.email = :email AND t.isDeleted = false")
    Optional<Teacher> findByEmailAndNotDeleted(String email);

    @Query("SELECT t FROM Teacher t WHERE t.isActive = true AND t.isDeleted = false")
    List<Teacher> findAllActiveTeachers();

    @Query("SELECT t FROM Teacher t WHERE (LOWER(t.firstName) LIKE LOWER(CONCAT('%', :searchQuery, '%')) " +
            "OR LOWER(t.lastName) LIKE LOWER(CONCAT('%', :searchQuery, '%')) " +
            "OR LOWER(t.email) LIKE LOWER(CONCAT('%', :searchQuery, '%'))) " +
            "AND t.isDeleted = false")
    List<Teacher> searchTeachers(String searchQuery);
}
