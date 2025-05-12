package com.example.care4elders.services;

import com.example.care4elders.model.Medication;

import java.util.List;
import java.util.Optional;

public interface MedicationService {
    List<Medication> getAllMedications();
    Optional<Medication> getMedicationById(Long id);
    Medication addMedication(Medication medication);
    List<Medication> addMedicationsBulk(List<Medication> medications);
    Medication updateMedication(Long id, Medication updatedMedication);
    void deleteMedication(Long id);
    void deleteMedicationsBulk(List<Long> ids);

    Medication findByName(String medicationName);
}
