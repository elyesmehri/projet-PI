package com.example.care4elders.services;

import com.example.care4elders.controllers.DTO.ScheduleRequest;
import com.example.care4elders.controllers.DTO.ScheduleResponse;
import com.example.care4elders.model.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    // Helper method to convert Entity to Response DTO
    ScheduleResponse convertToDto(Schedule schedule);

    // Helper method to calculate total
    Integer calculateTotal(boolean morning, boolean noon, boolean night);

    // CREATE Schedule
    ScheduleResponse createSchedule(ScheduleRequest request);

    // READ Schedule by ID
    ScheduleResponse getScheduleById(Long id);

    // READ All Schedules
    List<ScheduleResponse> getAllSchedules();

    // UPDATE Schedule
    ScheduleResponse updateSchedule(Long id, ScheduleRequest request);

    // DELETE Schedule
    void deleteSchedule(Long id);

    // Get Schedules by Pillular ID
    List<ScheduleResponse> getSchedulesByPillularId(Long pillularId);

    // Find schedule for a specific pillular and date
    ScheduleResponse getScheduleByPillularAndDate(Long pillularId, LocalDate date);

}
