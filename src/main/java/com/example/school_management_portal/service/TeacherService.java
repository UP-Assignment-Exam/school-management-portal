package com.example.school_management_portal.service;


import com.example.school_management_portal.entity.Teacher;
import com.example.school_management_portal.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAllActive();
    }

    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findByIdAndNotDeleted(id);
    }

    public Teacher saveTeacher(Teacher teacher) {
        // Check if email already exists
        Optional<Teacher> existingTeacher = teacherRepository.findByEmailAndNotDeleted(teacher.getEmail());
        if (existingTeacher.isPresent() && !existingTeacher.get().getId().equals(teacher.getId())) {
            throw new RuntimeException("Teacher with email " + teacher.getEmail() + " already exists");
        }
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByIdAndNotDeleted(id);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();

            // Check if email already exists (excluding current teacher)
            Optional<Teacher> existingTeacher = teacherRepository.findByEmailAndNotDeleted(teacherDetails.getEmail());
            if (existingTeacher.isPresent() && !existingTeacher.get().getId().equals(id)) {
                throw new RuntimeException("Teacher with email " + teacherDetails.getEmail() + " already exists");
            }

            teacher.setFirstName(teacherDetails.getFirstName());
            teacher.setLastName(teacherDetails.getLastName());
            teacher.setEmail(teacherDetails.getEmail());
            teacher.setPhoneNumber(teacherDetails.getPhoneNumber());
            teacher.setActive(teacherDetails.isActive());
            teacher.setImageUrl(teacherDetails.getImageUrl());

            return teacherRepository.save(teacher);
        } else {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
    }

    public void deleteTeacher(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByIdAndNotDeleted(id);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            teacher.setDeleted(true); // Soft delete
            teacherRepository.save(teacher);
        } else {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
    }

    public List<Teacher> getActiveTeachers() {
        return teacherRepository.findAllActiveTeachers();
    }

    public List<Teacher> searchTeachers(String searchQuery) {
        return teacherRepository.searchTeachers(searchQuery);
    }

    public Optional<Teacher> findByEmail(String email) {
        return teacherRepository.findByEmailAndNotDeleted(email);
    }

    public boolean existsByEmail(String email) {
        return teacherRepository.findByEmailAndNotDeleted(email).isPresent();
    }
}
