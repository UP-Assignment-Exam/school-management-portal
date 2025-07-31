package com.example.school_management_portal.service;


import com.example.school_management_portal.entity.ClassEntity;
import com.example.school_management_portal.repository.ClassEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassEntityService {

    private final ClassEntityRepository classEntityRepository;

    @Autowired
    public ClassEntityService(ClassEntityRepository classEntityRepository) {
        this.classEntityRepository = classEntityRepository;
    }

    /**
     * Get all classes that are not soft deleted
     */
    @Transactional(readOnly = true)
    public List<ClassEntity> getAllClasses() {
        return classEntityRepository.findByIsDeletedFalse();
    }

    /**
     * Get class by ID if not soft deleted
     */
    @Transactional(readOnly = true)
    public Optional<ClassEntity> getById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return classEntityRepository.findByIdAndIsDeletedFalse(id);
    }

    /**
     * Create a new class
     */
    public ClassEntity create(ClassEntity classEntity) {
        if (classEntity == null) {
            throw new IllegalArgumentException("ClassEntity cannot be null");
        }

        // Validate business rules
        validateClassEntity(classEntity);

        // Check for duplicate name
        if (classEntityRepository.existsByNameIgnoreCaseAndIsDeletedFalse(classEntity.getName())) {
            throw new IllegalArgumentException("Class with name '" + classEntity.getName() + "' already exists");
        }

        // Ensure isDeleted is false for new entities
        classEntity.setDeleted(false);

        return classEntityRepository.save(classEntity);
    }

    /**
     * Update an existing class
     */
    public ClassEntity update(Long id, ClassEntity updated) {
        if (id == null || updated == null) {
            throw new IllegalArgumentException("ID and ClassEntity cannot be null");
        }

        Optional<ClassEntity> existingOpt = getById(id);
        if (existingOpt.isEmpty()) {
            throw new IllegalArgumentException("Class with ID " + id + " not found or has been deleted");
        }

        ClassEntity existing = existingOpt.get();

        // Validate business rules
        validateClassEntity(updated);

        // Check for duplicate name (excluding current entity)
        if (!existing.getName().equalsIgnoreCase(updated.getName()) &&
                classEntityRepository.existsByNameIgnoreCaseAndIdNotAndIsDeletedFalse(updated.getName(), id)) {
            throw new IllegalArgumentException("Class with name '" + updated.getName() + "' already exists");
        }

        // Update fields
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        existing.setMaxStudents(updated.getMaxStudents());

        // Update teacher if provided
        if (updated.getTeacher() != null) {
            existing.setTeacher(updated.getTeacher());
        }

        return classEntityRepository.save(existing);
    }

    /**
     * Soft delete a class
     */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Optional<ClassEntity> classEntityOpt = getById(id);
        if (classEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("Class with ID " + id + " not found or already deleted");
        }

        ClassEntity classEntity = classEntityOpt.get();
        classEntity.setDeleted(true);
        classEntityRepository.save(classEntity);
    }

    /**
     * Get classes by teacher ID
     */
    @Transactional(readOnly = true)
    public List<ClassEntity> getClassesByTeacher(Long teacherId) {
        if (teacherId == null) {
            return List.of();
        }
        return classEntityRepository.findByTeacherIdAndIsDeletedFalse(teacherId);
    }

    /**
     * Search classes by name
     */
    @Transactional(readOnly = true)
    public List<ClassEntity> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllClasses();
        }
        return classEntityRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name.trim());
    }

    /**
     * Get classes with available spots
     */
    @Transactional(readOnly = true)
    public List<ClassEntity> getClassesWithAvailableSpots() {
        return classEntityRepository.findClassesWithAvailableSpots();
    }

    /**
     * Get count of active classes
     */
    @Transactional(readOnly = true)
    public long getActiveClassCount() {
        return classEntityRepository.countByIsDeletedFalse();
    }

    /**
     * Validate class entity business rules
     */
    private void validateClassEntity(ClassEntity classEntity) {
        if (classEntity.getName() == null || classEntity.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Class name is required");
        }

        if (classEntity.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (classEntity.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required");
        }

        if (classEntity.getStartDate().isAfter(classEntity.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        if (classEntity.getMaxStudents() != null && classEntity.getMaxStudents() < 1) {
            throw new IllegalArgumentException("Maximum students must be at least 1");
        }
    }
}