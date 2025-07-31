package com.example.school_management_portal.repository;

import com.example.school_management_portal.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Find all non-deleted schedules
    @Query("SELECT s FROM Schedule s WHERE s.isDeleted = false")
    List<Schedule> findAllActive();

    // Find non-deleted schedule by ID
    @Query("SELECT s FROM Schedule s WHERE s.id = :id AND s.isDeleted = false")
    Optional<Schedule> findActiveById(Long id);

    // Find schedules by day of week (non-deleted)
    @Query("SELECT s FROM Schedule s WHERE s.dayOfWeek = :dayOfWeek AND s.isDeleted = false")
    List<Schedule> findByDayOfWeekAndNotDeleted(DayOfWeek dayOfWeek);

    // Find schedules by class entity (non-deleted)
    @Query("SELECT s FROM Schedule s WHERE s.classEntity.id = :classId AND s.isDeleted = false")
    List<Schedule> findByClassEntityIdAndNotDeleted(Long classId);
}