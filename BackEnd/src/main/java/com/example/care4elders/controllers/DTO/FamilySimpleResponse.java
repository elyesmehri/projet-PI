package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilySimpleResponse {
    private Long id;
    private String familyName; // Or whatever primary field you have in Family
    // Add other relevant simple fields from Family entity
}
