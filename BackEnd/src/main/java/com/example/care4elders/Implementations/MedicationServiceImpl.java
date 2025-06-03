package com.example.care4elders.Implementations;

import com.example.care4elders.model.Medication;
import com.example.care4elders.repository.MedicationRepository;
import com.example.care4elders.services.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    @Override
    public Optional<Medication> getMedicationById(Long id) {
        return medicationRepository.findById(id);
    }

    @Override
    public Medication addMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    @Override
    public List<Medication> addMedicationsBulk(List<Medication> medications) {
        return medicationRepository.saveAll(medications);
    }

    @Override
    public Medication updateMedication(Long id, Medication updatedMedication) {
        return medicationRepository.findById(id)
                .map(medication -> {
                    medication.medicationname(updatedMedication.getMedicationName());
                    medication.setId(updatedMedication.getId());
                    return medicationRepository.save(medication);
                })
                .orElseThrow(() -> new RuntimeException("Medication not found with id " + id));
    }

    @Override
    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }

    @Override
    public void deleteMedicationsBulk(List<Long> ids) {
        medicationRepository.deleteAllById(ids);
    }

    @Override
    public Medication findByName(String name) {
        return medicationRepository.findByMedicationName(name);
    }
}
