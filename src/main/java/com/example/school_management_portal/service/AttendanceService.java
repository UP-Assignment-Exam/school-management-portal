package com.example.school_management_portal.service;


import com.example.school_management_portal.entity.Attendance;
import com.example.school_management_portal.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository repository;

    public List<Attendance> getAllAttendances() {
        return repository.findByIsDeletedFalse();
    }

    public Optional<Attendance> getAttendanceById(Long id) {
        return repository.findById(id).filter(a -> !a.isDeleted());
    }

    public Attendance createAttendance(Attendance attendance) {
        return repository.save(attendance);
    }

    public Attendance updateAttendance(Long id, Attendance updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setDate(updated.getDate());
                    existing.setStatus(updated.getStatus());
                    existing.setRemarks(updated.getRemarks());
                    existing.setStudent(updated.getStudent());
                    existing.setClassEntity(updated.getClassEntity());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
    }

    public void deleteAttendance(Long id) {
        repository.findById(id).ifPresent(attendance -> {
            attendance.setDeleted(true);
            repository.save(attendance);
        });
    }
}