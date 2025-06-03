package com.example.care4elders.Implementations;

import com.example.care4elders.controllers.DTO.ScheduleRequest;
import com.example.care4elders.controllers.DTO.ScheduleResponse;
import com.example.care4elders.model.Pillular;
import com.example.care4elders.model.Schedule;
import com.example.care4elders.repository.PillularRepository;
import com.example.care4elders.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.care4elders.services.ScheduleService;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PillularRepository pillularRepository;

    // Helper method to convert Entity to Response DTO
    @Override
    public ScheduleResponse convertToDto(Schedule schedule) {
        ScheduleResponse dto = new ScheduleResponse();
        dto.setId(schedule.getId());
        dto.setScheduleDate(schedule.getScheduleDate());
        dto.setMorning(schedule.isMorning());
        dto.setNoon(schedule.isNoon());
        dto.setNight(schedule.isNight());
        dto.setTotal(schedule.getTotal());

        if (schedule.getPillular() != null) {
            dto.setPillularId(schedule.getPillular().getId());
            // Add context from Pillular:
            if (schedule.getPillular().getMedication() != null) {
                dto.setMedicationName(schedule.getPillular().getMedication().getMedicationName());
            }
            if (schedule.getPillular().getPatient() != null) {
                dto.setPatientName(schedule.getPillular().getPatient().getPatientName());
            }
        }
        return dto;
    }

    // Helper method to calculate total
    @Override
    public Integer calculateTotal(boolean morning, boolean noon, boolean night) {
        int total = 0;
        if (morning) total++;
        if (noon) total++;
        if (night) total++;
        return total;
    }

    // CREATE Schedule
    @Override
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        Pillular pillular = pillularRepository.findById(request.getPillularId())
                .orElseThrow(() -> new EntityNotFoundException("Pillular (Prescription) not found with ID: " + request.getPillularId()));

        Schedule schedule = new Schedule();
        schedule.setPillular(pillular);
        schedule.setScheduleDate(request.getScheduleDate());
        schedule.setMorning(request.isMorning());
        schedule.setNoon(request.isNoon());
        schedule.setNight(request.isNight());
        schedule.setTotal(calculateTotal(request.isMorning(), request.isNoon(), request.isNight()));

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToDto(savedSchedule);
    }

    // READ Schedule by ID
    @Override
    public ScheduleResponse getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + id));
        return convertToDto(schedule);
    }

    // READ All Schedules
    @Override
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // UPDATE Schedule
    @Override
    public ScheduleResponse updateSchedule(Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + id));

        // Update Pillular relationship only if ID is provided and changed
        if (request.getPillularId() != null && !request.getPillularId().equals(schedule.getPillular().getId())) {
            Pillular pillular = pillularRepository.findById(request.getPillularId())
                    .orElseThrow(() -> new EntityNotFoundException("Pillular (Prescription) not found with ID: " + request.getPillularId()));
            schedule.setPillular(pillular);
        }

        schedule.setScheduleDate(request.getScheduleDate());
        schedule.setMorning(request.isMorning());
        schedule.setNoon(request.isNoon());
        schedule.setNight(request.isNight());
        schedule.setTotal(calculateTotal(request.isMorning(), request.isNoon(), request.isNight())); // Recalculate total

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return convertToDto(updatedSchedule);
    }

    // DELETE Schedule
    @Override
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Schedule not found with ID: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    // Get Schedules by Pillular ID
    @Override
    public List<ScheduleResponse> getSchedulesByPillularId(Long pillularId) {
        if (!pillularRepository.existsById(pillularId)) {
            throw new EntityNotFoundException("Pillular (Prescription) not found with ID: " + pillularId);
        }
        return scheduleRepository.findByPillularId(pillularId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Find schedule for a specific pillular and date
    @Override
    public ScheduleResponse getScheduleByPillularAndDate(Long pillularId, LocalDate date) {
        Schedule schedule = scheduleRepository.findByPillularIdAndScheduleDate(pillularId, date)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found for Pillular ID " + pillularId + " on date " + date));
        return convertToDto(schedule);
    }

}
