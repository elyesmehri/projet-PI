package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleResponse {

    private Long id;
    private LocalDate scheduleDate;
    private boolean morning;
    private boolean noon;
    private boolean night;
    private Integer total; // The sum of taken pills for the day

    private Long pillularId;
    private String medicationName; // For context (from the Pillular's Medication)
    private String patientName;    // For context (from the Pillular's Patient)
}
