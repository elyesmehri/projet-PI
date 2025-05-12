package com.example.care4elders.repository;

import com.example.care4elders.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Medication findByMedicationName(String medicationName); // ✅ Utilise le bon champ
}
