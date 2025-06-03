package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class AppointmentRequest {

        private Long doctorId; // To link to a Doctor
        private Long familyId; // To link to a Family

        private LocalDateTime date;
        private Integer period; // Using Integer for nullable if needed, otherwise int
        private Double tariff; // Using Double for nullable if needed, otherwise double
        private int nature;
        private String emergency;
        private int state;
        private String description;

}
