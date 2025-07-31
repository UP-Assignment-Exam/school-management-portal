package com.example.school_management_portal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.internal.build.AllowNonPortable;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@AllowNonPortable
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleDTO {
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long classId;
    private String className;

}