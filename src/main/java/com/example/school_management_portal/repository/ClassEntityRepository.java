package com.example.school_management_portal.repository;

import com.example.school_management_portal.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.util.*;


@Repository
public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long> {

    /**
     * Find all classes that are not soft deleted
     */
    List<ClassEntity> findByIsDeletedFalse();

    /**
     * Find a class by ID that is not soft deleted
     */
    Optional<ClassEntity> findByIdAndIsDeletedFalse(Long id);

    /**
     * Find classes by teacher ID that are not soft deleted
     */
    @Query("SELECT c FROM ClassEntity c WHERE c.teacher.id = :teacherId AND c.isDeleted = false")
    List<ClassEntity> findByTeacherIdAndIsDeletedFalse(@Param("teacherId") Long teacherId);

    /**
     * Find classes by name containing text (case insensitive) that are not soft deleted
     */
    @Query("SELECT c FROM ClassEntity c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND c.isDeleted = false")
    List<ClassEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(@Param("name") String name);

    /**
     * Count active classes (not soft deleted)
     */
    long countByIsDeletedFalse();

    /**
     * Find classes with available spots (current students < max students)
     */
    @Query("SELECT c FROM ClassEntity c WHERE SIZE(c.students) < c.maxStudents AND c.isDeleted = false")
    List<ClassEntity> findClassesWithAvailableSpots();

    /**
     * Check if class exists by name (excluding current class ID for updates)
     */
    @Query("SELECT COUNT(c) > 0 FROM ClassEntity c WHERE LOWER(c.name) = LOWER(:name) AND c.id != :excludeId AND c.isDeleted = false")
    boolean existsByNameIgnoreCaseAndIdNotAndIsDeletedFalse(@Param("name") String name, @Param("excludeId") Long excludeId);

    /**
     * Check if class exists by name
     */
    boolean existsByNameIgnoreCaseAndIsDeletedFalse(String name);

    // Find all non-deleted classes
    @Query("SELECT c FROM ClassEntity c WHERE c.isDeleted = false")
    List<ClassEntity> findAllActive();

    // Find non-deleted class by ID
    @Query("SELECT c FROM ClassEntity c WHERE c.id = :id AND c.isDeleted = false")
    Optional<ClassEntity> findActiveById(Long id);

    // Find classes by name (non-deleted)
    @Query("SELECT c FROM ClassEntity c WHERE c.name LIKE %:name% AND c.isDeleted = false")
    List<ClassEntity> findByNameContainingAndNotDeleted(String name);
}