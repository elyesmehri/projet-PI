package com.example.care4elders.controllers;


import com.example.care4elders.controllers.DTO.ScheduleRequest;
import com.example.care4elders.controllers.DTO.ScheduleResponse;
import com.example.care4elders.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // CREATE Schedule
    @PostMapping("/create")
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        try {
            ScheduleResponse newSchedule = scheduleService.createSchedule(request);
            return new ResponseEntity<>(newSchedule, HttpStatus.CREATED); // 201 Created
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request if Pillular not found
        }
    }

    // READ All Schedules
    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponse>> getAllSchedules() {
        List<ScheduleResponse> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules); // 200 OK
    }

    // READ Schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> getScheduleById(@PathVariable Long id) {
        try {
            ScheduleResponse schedule = scheduleService.getScheduleById(id);
            return ResponseEntity.ok(schedule); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // UPDATE Schedule by ID
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequest request) {
        try {
            ScheduleResponse updatedSchedule = scheduleService.updateSchedule(id, request);
            return ResponseEntity.ok(updatedSchedule); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found if schedule or pillular not found
        }
    }

    // DELETE Schedule by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
