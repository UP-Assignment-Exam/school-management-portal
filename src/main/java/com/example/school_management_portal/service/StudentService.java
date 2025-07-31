package com.example.school_management_portal.service;


import com.example.school_management_portal.entity.Student;
import com.example.school_management_portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Get all non-deleted students
     */
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findByIsDeletedFalse();
    }

    /**
     * Get student by ID (only if not deleted)
     */
    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findByIdAndIsDeletedFalse(id);
    }

    /**
     * Create a new student
     */
    public Student createStudent(Student student) {
        // Validate email uniqueness
        if (studentRepository.existsByEmailAndIsDeletedFalse(student.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + student.getEmail());
        }

        // Set enrollment date if not provided
        if (student.getEnrollmentDate() == null) {
            student.setEnrollmentDate(LocalDate.now());
        }

        // Ensure student is not marked as deleted
        student.setDeleted(false);

        return studentRepository.save(student);
    }

    /**
     * Update an existing student
     */
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = studentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));

        // Validate email uniqueness (excluding current student)
        if (!existingStudent.getEmail().equals(updatedStudent.getEmail()) &&
                studentRepository.existsByEmailAndIdNotAndIsDeletedFalse(updatedStudent.getEmail(), id)) {
            throw new IllegalArgumentException("Email already exists: " + updatedStudent.getEmail());
        }

        // Update fields
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
        existingStudent.setGender(updatedStudent.getGender());
        existingStudent.setAddress(updatedStudent.getAddress());
        existingStudent.setEnrollmentDate(updatedStudent.getEnrollmentDate());
        existingStudent.setActive(updatedStudent.isActive());
        existingStudent.setImageUrl(updatedStudent.getImageUrl());

        return studentRepository.save(existingStudent);
    }

    /**
     * Soft delete a student (mark as deleted)
     */
    public void deleteStudent(Long id) {
        Student student = studentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));

        student.setDeleted(true);
        student.setActive(false); // Also deactivate when deleting
        studentRepository.save(student);
    }

    /**
     * Get active students only
     */
    @Transactional(readOnly = true)
    public List<Student> getActiveStudents() {
        return studentRepository.findByIsDeletedFalseAndIsActiveTrue();
    }

    /**
     * Search students by name
     */
    @Transactional(readOnly = true)
    public List<Student> searchStudentsByName(String searchTerm) {
        return studentRepository.searchStudentsByName(searchTerm);
    }

    /**
     * Check if email exists
     */
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return studentRepository.existsByEmailAndIsDeletedFalse(email);
    }

    /**
     * Get total count of active students
     */
    @Transactional(readOnly = true)
    public long getActiveStudentCount() {
        return studentRepository.countByIsDeletedFalseAndIsActiveTrue();
    }

    /**
     * Activate/Deactivate student
     */
    public Student toggleStudentStatus(Long id) {
        Student student = studentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));

        student.setActive(!student.isActive());
        return studentRepository.save(student);
    }
}