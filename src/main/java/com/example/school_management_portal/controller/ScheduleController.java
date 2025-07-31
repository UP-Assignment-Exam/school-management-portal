package com.example.school_management_portal.controller;

import com.example.school_management_portal.dto.ScheduleDTO;
import com.example.school_management_portal.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * GET /api/schedules - Get all schedules
     */
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        try {
            List<ScheduleDTO> schedules = scheduleService.getAllSchedulesDTO();
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/schedules/{id} - Get schedule by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        try {
            Optional<ScheduleDTO> schedule = scheduleService.getScheduleDTOById(id);

            if (schedule.isPresent()) {
                return ResponseEntity.ok(schedule.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/schedules - Create new schedule
     */
    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        try {
            ScheduleDTO createdSchedule = scheduleService.createScheduleFromDTO(scheduleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/schedules/{id} - Update existing schedule
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleDTO scheduleDTO) {
        try {
            ScheduleDTO updatedSchedule = scheduleService.updateScheduleFromDTO(id, scheduleDTO);
            return ResponseEntity.ok(updatedSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/schedules/{id} - Soft delete schedule
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}