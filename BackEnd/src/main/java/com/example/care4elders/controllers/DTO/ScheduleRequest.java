package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleRequest {

    private LocalDate scheduleDate;
    private boolean morning; // Corresponds to 1 (taken) or 0 (not taken)
    private boolean noon;
    private boolean night;
    private Long pillularId; // To link to the specific prescription
}

