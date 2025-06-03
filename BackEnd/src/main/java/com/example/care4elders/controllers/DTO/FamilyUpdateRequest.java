package com.example.care4elders.controllers.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyUpdateRequest {

    private String relative;
    private String relationship;
    private String address;
    private String phoneNumber;
    private String password;
    private String advice;
}
