package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponse {
    private Long id;
    private Long doctorId;
    private String doctorName; // Optional: include doctor's name for convenience
    private Long familyId;
    private String familyName; // Optional: include family's name for convenience

    private LocalDateTime date;
    private Integer period;
    private Double tariff;
    private int nature;
    private String emergency;
    private int state;
    private String description;

}
