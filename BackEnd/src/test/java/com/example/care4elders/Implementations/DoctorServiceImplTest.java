package com.example.care4elders.Implementations;

import com.example.care4elders.model.Appointment; // Assuming these models exist for Doctor relationships
import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Family;
import com.example.care4elders.model.Insurance;
import com.example.care4elders.model.Patient;
import com.example.care4elders.model.Visit;
import com.example.care4elders.repository.AppointmentRepository;
import com.example.care4elders.repository.DoctorRepository;
import com.example.care4elders.repository.FamilyRepository;
import com.example.care4elders.repository.InsuranceRepository;
import com.example.care4elders.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException; // Standard JPA exception for not found
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    void testGetAllDoctors() {
        List<Doctor> mockDoctors = List.of(new Doctor(), new Doctor());
        Mockito.when(doctorRepository.findAll()).thenReturn(mockDoctors);

        List<Doctor> result = doctorService.getAllDoctors();

        Assertions.assertEquals(2, result.size());
        Mockito.verify(doctorRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testSaveDoctor() {
        Doctor doctor = new Doctor();
        doctor.setDoctorname("John");

        Mockito.when(doctorRepository.save(Mockito.any(Doctor.class))).thenReturn(doctor);

        Doctor result = doctorService.save(doctor);

        Assertions.assertEquals("John", result.getDoctorname());
    }

    @Test
    void testGetDoctorById() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Optional<Doctor> result = doctorService.getDoctorById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(1L, result.get().getId());
    }

    @Test
    void testDeleteById_Success() {
        Long id = 1L;
        Mockito.when(doctorRepository.existsById(id)).thenReturn(true);

        boolean deleted = doctorService.deleteById(id);

        Assertions.assertTrue(deleted);
        Mockito.verify(doctorRepository).deleteById(id);
    }

    @Test
    void testDeleteById_NotFound() {
        Long id = 1L;
        Mockito.when(doctorRepository.existsById(id)).thenReturn(false);

        boolean deleted = doctorService.deleteById(id);

        Assertions.assertFalse(deleted);
        Mockito.verify(doctorRepository, Mockito.never()).deleteById(id);
    }
}
