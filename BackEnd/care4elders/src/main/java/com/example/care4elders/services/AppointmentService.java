package com.example.care4elders.services;

import com.example.care4elders.model.Appointment;

import java.util.List;


public interface AppointmentService {

    Appointment save(Appointment appointment);
    boolean deleteById(Long id);
    boolean updateAppointment(Long id, Appointment updated);

    Appointment findById(Long id);

    void deleteAllAppointments();

    List<Appointment> getAppointmentsByDoctorId(Long doctorId);
}
