package com.example.care4elders.Implementations;

import com.example.care4elders.model.Pillular;
import com.example.care4elders.repository.PillularRepository;
import com.example.care4elders.services.PillularService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.care4elders.controllers.DTO.PillularRequest;
import com.example.care4elders.controllers.DTO.PillularResponse;
import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Insurance;
import com.example.care4elders.model.Medication;
import com.example.care4elders.model.Patient;
import com.example.care4elders.repository.DoctorRepository;
import com.example.care4elders.repository.InsuranceRepository;
import com.example.care4elders.repository.MedicationRepository;
import com.example.care4elders.repository.PatientRepository;


import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PillularServiceImpl implements PillularService {

    private final PillularRepository pillularRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;
    private final InsuranceRepository insuranceRepository;

    @Override
    public PillularResponse convertToDto(Pillular pillular) {

        PillularResponse dto = new PillularResponse();
        dto.setId(pillular.getId());
        dto.setPrescriptionDate(pillular.getPrescriptionDate());
        dto.setDosage(pillular.getDosage());
        dto.setFrequency(pillular.getFrequency());
        dto.setDuration(pillular.getDuration());
        dto.setQuantity(pillular.getQuantity());
        dto.setInstructions(pillular.getInstructions());

        if (pillular.getDoctor() != null) {
            dto.setDoctorId(pillular.getDoctor().getId());
            dto.setDoctorName(pillular.getDoctor().getDoctorname());
        }
        if (pillular.getPatient() != null) {
            dto.setPatientId(pillular.getPatient().getId());
            dto.setPatientName(pillular.getPatient().getPatientName());
        }
        if (pillular.getMedication() != null) {
            dto.setMedicationId(pillular.getMedication().getId());
            dto.setMedicationName(pillular.getMedication().getMedicationName());
            dto.setMedicationCategory(pillular.getMedication().getCategory());
        }
        if (pillular.getInsurance() != null) {
            dto.setInsuranceId(pillular.getInsurance().getId());
            dto.setInsuranceProviderName(pillular.getInsurance().getProviderName());
            dto.setInsurancePolicyNumber(pillular.getInsurance().getPolicyNumber());
        }

        dto.setIsCoveredByInsurance(pillular.getCoveredByInsurance());
        dto.setCoveredAmount(pillular.getCoveredAmount());
        dto.setPatientOutOfPocket(pillular.getPatientOutOfPocket());

        return dto;
    }

    // CREATE Pillular (Prescription)
    @Override
    public PillularResponse createPillular(PillularRequest request) {
        Pillular pillular = new Pillular();

        // Fetch and set relationships
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + request.getDoctorId()));
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + request.getPatientId()));
        Medication medication = medicationRepository.findById(request.getMedicationId())
                .orElseThrow(() -> new EntityNotFoundException("Medication (catalog) not found with ID: " + request.getMedicationId()));

        Insurance insurance = null;
        if (request.getInsuranceId() != null) {
            insurance = insuranceRepository.findById(request.getInsuranceId())
                    .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + request.getInsuranceId()));
        }

        pillular.setDoctor(doctor);
        pillular.setPatient(patient);
        pillular.setMedication(medication);
        pillular.setInsurance(insurance); // Can be null

        // Set prescription details
        pillular.setPrescriptionDate(request.getPrescriptionDate());
        pillular.setDosage(request.getDosage());
        pillular.setFrequency(request.getFrequency());
        pillular.setDuration(request.getDuration());
        pillular.setQuantity(request.getQuantity());
        pillular.setInstructions(request.getInstructions());

        // Set insurance coverage details (This is where actual calculation logic would go)
        // For now, we'll just set them directly from the request.
        pillular.setCoveredByInsurance(request.getIsCoveredByInsurance());
        pillular.setCoveredAmount(request.getCoveredAmount());
        pillular.setPatientOutOfPocket(request.getPatientOutOfPocket());

        Pillular savedPillular = pillularRepository.save(pillular);
        return convertToDto(savedPillular);
    }

    // READ Pillular by ID
    @Override
    public PillularResponse getPillularById(Long id) {
        Pillular pillular = pillularRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pillular (Prescription) not found with ID: " + id));
        return convertToDto(pillular);
    }

    // READ All Pillulars
    @Override
    public List<PillularResponse> getAllPillulars() {
        return pillularRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // UPDATE Pillular by ID
    @Override
    public PillularResponse updatePillular(Long id, PillularRequest request) {
        Pillular pillular = pillularRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pillular (Prescription) not found with ID: " + id));

        // Update relationships if IDs are provided and changed
        if (request.getDoctorId() != null && (pillular.getDoctor() == null || !request.getDoctorId().equals(pillular.getDoctor().getId()))) {
            Doctor doctor = doctorRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + request.getDoctorId()));
            pillular.setDoctor(doctor);
        }
        if (request.getPatientId() != null && (pillular.getPatient() == null || !request.getPatientId().equals(pillular.getPatient().getId()))) {
            Patient patient = patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + request.getPatientId()));
            pillular.setPatient(patient);
        }
        if (request.getMedicationId() != null && (pillular.getMedication() == null || !request.getMedicationId().equals(pillular.getMedication().getId()))) {
            Medication medication = medicationRepository.findById(request.getMedicationId())
                    .orElseThrow(() -> new EntityNotFoundException("Medication (catalog) not found with ID: " + request.getMedicationId()));
            pillular.setMedication(medication);
        }
        // Handle insurance update (allowing it to be set to null or updated)
        if (request.getInsuranceId() != null) {
            Insurance insurance = insuranceRepository.findById(request.getInsuranceId())
                    .orElseThrow(() -> new EntityNotFoundException("Insurance not found with ID: " + request.getInsuranceId()));
            pillular.setInsurance(insurance);
        } else {
            pillular.setInsurance(null); // Explicitly set to null if no insuranceId is provided
        }

        // Update prescription details
        pillular.setPrescriptionDate(request.getPrescriptionDate());
        pillular.setDosage(request.getDosage());
        pillular.setFrequency(request.getFrequency());
        pillular.setDuration(request.getDuration());
        pillular.setQuantity(request.getQuantity());
        pillular.setInstructions(request.getInstructions());

        // Update insurance coverage details
        pillular.setCoveredByInsurance(request.getIsCoveredByInsurance());
        pillular.setCoveredAmount(request.getCoveredAmount());
        pillular.setPatientOutOfPocket(request.getPatientOutOfPocket());

        Pillular updatedPillular = pillularRepository.save(pillular);
        return convertToDto(updatedPillular);
    }

    // DELETE Pillular by ID
    @Override
    public void deletePillular(Long id) {
        if (!pillularRepository.existsById(id)) {
            throw new EntityNotFoundException("Pillular (Prescription) not found with ID: " + id);
        }
        pillularRepository.deleteById(id);
    }

    @Override
    public List<PillularResponse> getPillularsByDoctorId(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new EntityNotFoundException("Doctor not found with ID: " + doctorId);
        }
        return pillularRepository.findByDoctorId(doctorId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PillularResponse> getPillularsByPatientId(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException("Patient not found with ID: " + patientId);
        }
        return pillularRepository.findByPatientId(patientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PillularResponse> getPillularsByMedicationId(Long medicationId) {
        if (!medicationRepository.existsById(medicationId)) {
            throw new EntityNotFoundException("Medication (catalog) not found with ID: " + medicationId);
        }
        return pillularRepository.findByMedicationId(medicationId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PillularResponse> getPillularsByInsuranceId(Long insuranceId) {
        if (!insuranceRepository.existsById(insuranceId)) {
            throw new EntityNotFoundException("Insurance not found with ID: " + insuranceId);
        }
        return pillularRepository.findByInsuranceId(insuranceId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
