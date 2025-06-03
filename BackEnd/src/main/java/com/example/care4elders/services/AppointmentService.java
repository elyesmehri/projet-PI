package com.example.care4elders.services;

import com.example.care4elders.controllers.DTO.AppointmentRequest;
import com.example.care4elders.controllers.DTO.AppointmentResponse;
import com.example.care4elders.model.Appointment;

import java.util.List;


public interface AppointmentService {
    // Helper method to convert Entity to Response DTO
    AppointmentResponse convertToDto(Appointment appointment);


    // CREATE
    AppointmentResponse createAppointment(AppointmentRequest request);

    // READ (by ID)
    AppointmentResponse getAppointmentById(Long id);

    // READ (all)
    List<AppointmentResponse> getAllAppointments();

    // UPDATE
    AppointmentResponse updateAppointment(Long id, AppointmentRequest request);

    // DELETE
    void deleteAppointment(Long id);

    List<AppointmentResponse> getAppointmentsByDoctorId(Long doctorId);

    List<AppointmentResponse> getAppointmentsByFamilyId(Long familyId);
}
