package com.example.care4elders.controllers;

import com.example.care4elders.controllers.DTO.ScheduleResponse; // <<< Import ScheduleResponse
import com.example.care4elders.services.ScheduleService; // <<< Import ScheduleService
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate; // For schedule date parameter
import java.util.List;

import com.example.care4elders.services.PillularService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pillulier")
@RequiredArgsConstructor
public class PillularController {

    private final PillularService pillularService;
    private final ScheduleService scheduleService;

    @GetMapping("/{pillularId}/schedules")
    public ResponseEntity<List<ScheduleResponse>> getPillularSchedules(@PathVariable Long pillularId) {
        try {
            List<ScheduleResponse> schedules = scheduleService.getSchedulesByPillularId(pillularId);
            return ResponseEntity.ok(schedules);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{pillularId}/schedules/date/{date}")
    public ResponseEntity<ScheduleResponse> getPillularScheduleByDate(
            @PathVariable Long pillularId,
            @PathVariable LocalDate date) { // Spring automatically converts YYYY-MM-DD string to LocalDate
        try {
            ScheduleResponse schedule = scheduleService.getScheduleByPillularAndDate(pillularId, date);
            return ResponseEntity.ok(schedule);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
