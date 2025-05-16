package com.example.care4elders.Implementations;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Insurance;
import com.example.care4elders.repository.DoctorRepository;
import com.example.care4elders.repository.InsuranceRepository;
import com.example.care4elders.services.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
/*
@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private InsuranceRepository insuranceRepository;
    private DoctorRepository doctorRepository;

    @Override
    public void addDoctorToInsurance(Long insuranceId, Long doctorId) {

        Optional<Insurance> insuranceOptional = insuranceRepository.findById(insuranceId);
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (insuranceOptional.isPresent() && doctorOptional.isPresent()) {
            Insurance insurance = insuranceOptional.get();
            Doctor doctor = doctorOptional.get();

            insurance.addAvailableDoctor(doctor);
            doctor.addAcceptedInsurance(insurance);

            insuranceRepository.save(Insurance);
            doctorRepository.save(doctor);

        } else {
            // Handle the case where insurance or doctor is not found
            throw new RuntimeException("Insurance or Doctor not found.");
        }
    }

}
*/
