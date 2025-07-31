package com.example.school_management_portal.service;

import com.example.school_management_portal.dto.ScheduleDTO;
import com.example.school_management_portal.entity.ClassEntity;
import com.example.school_management_portal.entity.Schedule;
import com.example.school_management_portal.repository.ClassEntityRepository;
import com.example.school_management_portal.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ClassEntityRepository classRepository;

    /**
     * Get all non-deleted schedules
     */
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAllActive();
    }

    /**
     * Get all schedules as DTOs to avoid circular references
     */
    public List<ScheduleDTO> getAllSchedulesDTO() {
        return scheduleRepository.findAllActive().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get schedule by ID (only if not deleted)
     */
    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findActiveById(id);
    }

    /**
     * Get schedule DTO by ID
     */
    public Optional<ScheduleDTO> getScheduleDTOById(Long id) {
        return scheduleRepository.findActiveById(id)
                .map(this::convertToDTO);
    }

    /**
     * Create new schedule
     */
    public Schedule createSchedule(Schedule schedule) {
        // Validate that class entity exists
        if (schedule.getClassEntity() != null && schedule.getClassEntity().getId() != null) {
            Optional<ClassEntity> classEntity = classRepository.findById(schedule.getClassEntity().getId());
            if (classEntity.isPresent()) {
                schedule.setClassEntity(classEntity.get());
            } else {
                throw new RuntimeException("Class entity not found with ID: " + schedule.getClassEntity().getId());
            }
        }

        schedule.setDeleted(false);
        return scheduleRepository.save(schedule);
    }

    /**
     * Create schedule from DTO
     */
    public ScheduleDTO createScheduleFromDTO(ScheduleDTO scheduleDTO) {
        Schedule schedule = convertFromDTO(scheduleDTO);
        Schedule savedSchedule = createSchedule(schedule);
        return convertToDTO(savedSchedule);
    }

    /**
     * Update existing schedule
     */
    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        Optional<Schedule> existingSchedule = scheduleRepository.findActiveById(id);

        if (existingSchedule.isPresent()) {
            Schedule schedule = existingSchedule.get();

            // Update fields
            schedule.setDayOfWeek(updatedSchedule.getDayOfWeek());
            schedule.setStartTime(updatedSchedule.getStartTime());
            schedule.setEndTime(updatedSchedule.getEndTime());

            // Update class entity if provided
            if (updatedSchedule.getClassEntity() != null && updatedSchedule.getClassEntity().getId() != null) {
                Optional<ClassEntity> classEntity = classRepository.findById(updatedSchedule.getClassEntity().getId());
                if (classEntity.isPresent()) {
                    schedule.setClassEntity(classEntity.get());
                } else {
                    throw new RuntimeException("Class entity not found with ID: " + updatedSchedule.getClassEntity().getId());
                }
            }

            return scheduleRepository.save(schedule);
        } else {
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
    }

    /**
     * Update schedule from DTO
     */
    public ScheduleDTO updateScheduleFromDTO(Long id, ScheduleDTO scheduleDTO) {
        Schedule schedule = convertFromDTO(scheduleDTO);
        Schedule updatedSchedule = updateSchedule(id, schedule);
        return convertToDTO(updatedSchedule);
    }

    /**
     * Soft delete schedule (set isDeleted = true)
     */
    public void deleteSchedule(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findActiveById(id);

        if (schedule.isPresent()) {
            Schedule scheduleToDelete = schedule.get();
            scheduleToDelete.setDeleted(true);
            scheduleRepository.save(scheduleToDelete);
        } else {
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
    }

    /**
     * Convert Schedule entity to DTO
     */
    private ScheduleDTO convertToDTO(Schedule schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getClassEntity() != null ? schedule.getClassEntity().getId() : null,
                schedule.getClassEntity() != null ? schedule.getClassEntity().getName() : null
        );
    }

    /**
     * Convert DTO to Schedule entity
     */
    private Schedule convertFromDTO(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setId(dto.getId());
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());

        if (dto.getClassId() != null) {
            ClassEntity classEntity = new ClassEntity();
            classEntity.setId(dto.getClassId());
            schedule.setClassEntity(classEntity);
        }

        return schedule;
    }
}