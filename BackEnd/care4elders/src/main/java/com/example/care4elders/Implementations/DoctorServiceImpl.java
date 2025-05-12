package com.example.care4elders.Implementations;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.repository.DoctorRepository;
import com.example.care4elders.repository.PatientRepository;
import com.example.care4elders.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository  doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> addDoctorsBulk(List<Doctor> doctors) {
        return doctorRepository.saveAll(doctors);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctor.setDoctorname(updatedDoctor.getDoctorname());
                    doctor.setId(updatedDoctor.getId());
                    return doctorRepository.save(doctor);
                })
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));
    }

    @Override
    public boolean validateDoctorLogin(String doctorname, String password) {
        Doctor doctor = doctorRepository.findByDoctorname(doctorname);
        return doctor != null && doctor.getPassword().equals(password);
    }


    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public void deleteDoctorsBulk(List<Long> ids) {
        doctorRepository.deleteAllById(ids);
    }

    @Override
    public Boolean checkDoctorCredentials(Map<String, String> credentials) {
        return null;
    }

    @Override
    public boolean authenticateDoctor(String doctorname, String password) {
        Doctor doctor = doctorRepository.findByDoctorname(doctorname);
        return doctor != null && doctor.getPassword().equals(password);
    }

    @Override
    public boolean updateDoctorPassword(String doctorname, String newPassword) {
        return false;
    }

    @Override
    public boolean updatePassword(String doctorname, String newPassword) {
        Optional<Doctor> doctorOpt = Optional.ofNullable(doctorRepository.findByDoctorname(doctorname));

        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            doctor.setPassword(newPassword);
            doctorRepository.save(doctor);
            return true;
        }

        return false;
    }

    @Override
    public void deleteAllDoctors() {
        doctorRepository.deleteAll();
    }

    @Override
    public boolean updateScoreByName(String doctorName, int newScore) {
        List<Doctor> doctors = doctorRepository.findByDoctornameContaining(doctorName);

        if (!doctors.isEmpty()) {
            Doctor doctor = doctors.get(0);
            doctor.setScore(newScore);
            doctorRepository.save(doctor);
            return true;
        }

        return false;
    }
/*
    @Override
    public Doctor findByName(String name) {
        return doctorRepository.findByDoctorname(name);
    }
*/
    @Override
    public Doctor findByName(String name) {
        return doctorRepository.findByDoctorname(name);

    }
}
