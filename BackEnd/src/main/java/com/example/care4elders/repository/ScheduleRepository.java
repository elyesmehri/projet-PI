package com.example.care4elders.repository;

import com.example.care4elders.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Custom finder methods
    List<Schedule> findByPillularId(Long pillularId);
    Optional<Schedule> findByPillularIdAndScheduleDate(Long pillularId, LocalDate scheduleDate);

}
