package com.example.care4elders.Implementations;

import com.example.care4elders.controllers.DTO.AppointmentRequest;
import com.example.care4elders.controllers.DTO.AppointmentResponse;
import com.example.care4elders.model.Appointment;
import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Family;
import com.example.care4elders.repository.AppointmentRepository;
import com.example.care4elders.repository.DoctorRepository;
import com.example.care4elders.repository.FamilyRepository;
import com.example.care4elders.services.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final FamilyRepository familyRepository;


    // Helper method to convert Entity to Response DTO
    @Override
    public AppointmentResponse convertToDto(Appointment appointment) {

        AppointmentResponse dto = new AppointmentResponse();
        dto.setId(appointment.getId());
        dto.setDate(appointment.getDate());
        dto.setPeriod(appointment.getPeriod());
        dto.setTariff(appointment.getTariff());
        dto.setNature(appointment.getNature());
        dto.setEmergency(appointment.getEmergency());
        dto.setState(appointment.getState());
        dto.setDescription(appointment.getDescription());

        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getId());
            dto.setDoctorName(appointment.getDoctor().getDoctorname()); // Include name
        }
        if (appointment.getFamily() != null) {
            dto.setFamilyId(appointment.getFamily().getId());
            dto.setFamilyName(appointment.getFamily().getFamilyname()); // Include name
        }
        return dto;
    }

    // CREATE
    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Appointment appointment = new Appointment();

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + request.getDoctorId()));
        Family family = familyRepository.findById(request.getFamilyId())
                .orElseThrow(() -> new EntityNotFoundException("Family not found with ID: " + request.getFamilyId()));

        appointment.setDoctor(doctor);
        appointment.setFamily(family);
        appointment.setDate(request.getDate());
        appointment.setPeriod(request.getPeriod());
        appointment.setTariff(request.getTariff());
        appointment.setNature(request.getNature());
        appointment.setEmergency(request.getEmergency());
        appointment.setState(request.getState());
        appointment.setDescription(request.getDescription());

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToDto(savedAppointment);
    }

    // READ (by ID)
    @Override
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id));
        return convertToDto(appointment);
    }

    // READ (all)
    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToDto) // Use the helper method to map each entity to a DTO
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id));

        // Update relationships only if IDs are provided in the request
        if (request.getDoctorId() != null && !request.getDoctorId().equals(appointment.getDoctor().getId())) {
            Doctor doctor = doctorRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + request.getDoctorId()));
            appointment.setDoctor(doctor);
        }
        if (request.getFamilyId() != null && !request.getFamilyId().equals(appointment.getFamily().getId())) {
            Family family = familyRepository.findById(request.getFamilyId())
                    .orElseThrow(() -> new EntityNotFoundException("Family not found with ID: " + request.getFamilyId()));
            appointment.setFamily(family);
        }

        // Update scalar fields
        appointment.setDate(request.getDate());
        appointment.setPeriod(request.getPeriod());
        appointment.setTariff(request.getTariff());
        appointment.setNature(request.getNature());
        appointment.setEmergency(request.getEmergency());
        appointment.setState(request.getState());
        appointment.setDescription(request.getDescription());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return convertToDto(updatedAppointment);
    }

    // DELETE
    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDoctorId(Long doctorId) {
        // Optional: Validate if the doctor exists first
        if (!doctorRepository.existsById(doctorId)) {
            throw new EntityNotFoundException("Doctor not found with ID: " + doctorId);
        }
        // Fetch appointments by doctorId and convert to DTOs
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByFamilyId(Long familyId) {
        // Optional: Validate if the family exists first
        if (!familyRepository.existsById(familyId)) {
            throw new EntityNotFoundException("Family not found with ID: " + familyId);
        }
        // Fetch appointments by familyId and convert to DTOs
        return appointmentRepository.findByFamilyId(familyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
