package com.example.school_management_portal.repository;

import com.example.school_management_portal.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByClassEntityId(Long classId);
}
