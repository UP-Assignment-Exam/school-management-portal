package com.example.school_management_portal.controller;


import com.example.school_management_portal.dto.StudentDto;
import com.example.school_management_portal.entity.Student;
import com.example.school_management_portal.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentService studentService;

    /**
     * GET /api/students - Get all students
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", students);
            response.put("message", "Students retrieved successfully");
            response.put("count", students.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving students: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/students/{id} - Get student by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable Long id) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            Map<String, Object> response = new HashMap<>();

            if (student.isPresent()) {
                response.put("success", true);
                response.put("data", student.get());
                response.put("message", "Student found successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Student not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * POST /api/students - Create new student
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createStudent(@RequestBody StudentDto studentDto) {
        try {
            // Map DTO to Entity
            Student student = modelMapper.map(studentDto, Student.class);

            Student createdStudent = studentService.createStudent(student);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", createdStudent);
            response.put("message", "Student created successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error creating student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * PUT /api/students/{id} - Update student
     */
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        try {
            // Map DTO to Entity
            Student student = modelMapper.map(studentDto, Student.class);

            Student updatedStudent = studentService.updateStudent(id, student);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", updatedStudent);
            response.put("message", "Student updated successfully");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error updating student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * DELETE /api/students/{id} - Soft delete student
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Student deleted successfully");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error deleting student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/students/active - Get only active students
     */
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveStudents() {
        try {
            List<Student> students = studentService.getActiveStudents();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", students);
            response.put("message", "Active students retrieved successfully");
            response.put("count", students.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving active students: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * GET /api/students/search?q={searchTerm} - Search students by name
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchStudents(@RequestParam("q") String searchTerm) {
        try {
            List<Student> students = studentService.searchStudentsByName(searchTerm);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", students);
            response.put("message", "Search completed successfully");
            response.put("count", students.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error searching students: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * PUT /api/students/{id}/toggle-status - Toggle student active status
     */
    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<Map<String, Object>> toggleStudentStatus(@PathVariable Long id) {
        try {
            Student student = studentService.toggleStudentStatus(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", student);
            response.put("message", "Student status updated successfully");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error updating student status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}