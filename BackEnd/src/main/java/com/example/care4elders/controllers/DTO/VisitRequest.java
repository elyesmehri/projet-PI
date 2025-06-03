package com.example.care4elders.controllers.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class VisitRequest {

    private LocalDateTime visitDate;
    @JsonProperty("isAssisted")
    private boolean isAssisted; // Reflects the boolean in the entity
    private Long doctorId;      // To link to a Doctor
    private Long patientId;     // To link to a Patient

}
