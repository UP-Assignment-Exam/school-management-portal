package com.example.school_management_portal.repository;

import com.example.school_management_portal.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByClassEntityIdAndDate(Long classId, LocalDate date);
}