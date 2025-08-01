package com.example.school_management_portal.controller;

import com.example.school_management_portal.dto.AttendanceDto;
import com.example.school_management_portal.entity.Attendance;
import com.example.school_management_portal.entity.ClassEntity;
import com.example.school_management_portal.entity.Student;
import com.example.school_management_portal.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService service;

    @GetMapping
    public ResponseEntity<List<Attendance>> getAll() {
        return ResponseEntity.ok(service.getAllAttendances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getById(@PathVariable Long id) {
        return service.getAttendanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Attendance> create(@RequestBody AttendanceDto dto) {
        Attendance attendance = new Attendance();

        attendance.setDate(LocalDate.parse(dto.getDate()));
        attendance.setStatus(dto.getStatus());
        attendance.setRemarks(dto.getRemarks());

        Student student = new Student();
        student.setId(dto.getStudentId());
        attendance.setStudent(student);

        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(dto.getClassId());
        attendance.setClassEntity(classEntity);

        return ResponseEntity.ok(service.createAttendance(attendance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> update(@PathVariable Long id, @RequestBody AttendanceDto dto) {
        Attendance attendance = new Attendance();

        attendance.setDate(LocalDate.parse(dto.getDate()));
        attendance.setStatus(dto.getStatus());
        attendance.setRemarks(dto.getRemarks());

        Student student = new Student();
        student.setId(dto.getStudentId());
        attendance.setStudent(student);

        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(dto.getClassId());
        attendance.setClassEntity(classEntity);

        return ResponseEntity.ok(service.updateAttendance(id, attendance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteAttendance(id);
        return ResponseEntity.ok().build();
    }
}
