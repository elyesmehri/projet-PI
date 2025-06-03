package com.example.care4elders.controllers.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class VisitResponse {

    private Long id;
    private LocalDateTime visitDate;

    @JsonProperty("isAssisted")
    private boolean isAssisted;

    private Long doctorId;
    private String doctorName;

    private Long patientId;
    private String patientName;

}
