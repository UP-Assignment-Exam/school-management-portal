package com.example.school_management_portal.repository;

import com.example.school_management_portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find all non-deleted students
    List<Student> findByIsDeletedFalse();

    // Find by ID if not deleted
    Optional<Student> findByIdAndIsDeletedFalse(Long id);

    // Find by email if not deleted
    Optional<Student> findByEmailAndIsDeletedFalse(String email);

    // Find active students only
    List<Student> findByIsDeletedFalseAndIsActiveTrue();

    // Search students by name (case-insensitive)
    @Query("SELECT s FROM Student s WHERE s.isDeleted = false AND " +
            "(LOWER(s.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Student> searchStudentsByName(@Param("searchTerm") String searchTerm);

    // Count active students
    long countByIsDeletedFalseAndIsActiveTrue();

    // Check if email exists (excluding deleted records)
    boolean existsByEmailAndIsDeletedFalse(String email);

    // Check if email exists for update (excluding current student and deleted records)
    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.email = :email AND s.id != :studentId AND s.isDeleted = false")
    boolean existsByEmailAndIdNotAndIsDeletedFalse(@Param("email") String email, @Param("studentId") Long studentId);
}