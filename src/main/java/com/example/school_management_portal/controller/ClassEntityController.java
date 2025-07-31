package com.example.school_management_portal.controller;

import com.example.school_management_portal.entity.ClassEntity;
import com.example.school_management_portal.service.ClassEntityService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*")
public class ClassEntityController {

    private final ClassEntityService classEntityService;

    @Autowired
    public ClassEntityController(ClassEntityService classEntityService) {
        this.classEntityService = classEntityService;
    }

    /**
     * GET /api/classes - Get all classes
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllClasses(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "false") boolean availableOnly) {

        try {
            List<ClassEntity> classes;

            if (availableOnly) {
                classes = classEntityService.getClassesWithAvailableSpots();
            } else if (search != null && !search.trim().isEmpty()) {
                classes = classEntityService.searchByName(search);
            } else {
                classes = classEntityService.getAllClasses();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("classes", classes);
            response.put("total", classes.size());
            response.put("message", "Classes retrieved successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve classes");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/classes/{id} - Get class by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getClassById(@PathVariable Long id) {
        try {
            Optional<ClassEntity> classEntityOpt = classEntityService.getById(id);

            if (classEntityOpt.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Class not found");
                errorResponse.put("message", "Class with ID " + id + " not found or has been deleted");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("class", classEntityOpt.get());
            response.put("message", "Class retrieved successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve class");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * POST /api/classes - Create new class
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createClass(@Valid @RequestBody ClassEntity classEntity) {
        try {
            ClassEntity createdClass = classEntityService.create(classEntity);

            Map<String, Object> response = new HashMap<>();
            response.put("class", createdClass);
            response.put("message", "Class created successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Validation error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to create class");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * PUT /api/classes/{id} - Update class
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateClass(
            @PathVariable Long id,
            @Valid @RequestBody ClassEntity classEntity) {

        try {
            ClassEntity updatedClass = classEntityService.update(id, classEntity);

            Map<String, Object> response = new HashMap<>();
            response.put("class", updatedClass);
            response.put("message", "Class updated successfully");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Validation error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to update class");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * DELETE /api/classes/{id} - Soft delete class
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteClass(@PathVariable Long id) {
        try {
            classEntityService.delete(id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Class deleted successfully");
            response.put("id", id);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Class not found");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to delete class");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/classes/teacher/{teacherId} - Get classes by teacher
     */
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<Map<String, Object>> getClassesByTeacher(@PathVariable Long teacherId) {
        try {
            List<ClassEntity> classes = classEntityService.getClassesByTeacher(teacherId);

            Map<String, Object> response = new HashMap<>();
            response.put("classes", classes);
            response.put("total", classes.size());
            response.put("message", "Teacher classes retrieved successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve teacher classes");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/classes/stats - Get class statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getClassStats() {
        try {
            long totalClasses = classEntityService.getActiveClassCount();
            List<ClassEntity> availableClasses = classEntityService.getClassesWithAvailableSpots();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalActiveClasses", totalClasses);
            stats.put("classesWithAvailableSpots", availableClasses.size());

            Map<String, Object> response = new HashMap<>();
            response.put("stats", stats);
            response.put("message", "Class statistics retrieved successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve class statistics");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}