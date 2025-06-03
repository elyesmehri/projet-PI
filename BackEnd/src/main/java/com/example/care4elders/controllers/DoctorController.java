package com.example.care4elders.controllers;

import com.example.care4elders.controllers.DTO.*;
import com.example.care4elders.controllers.DTO.VisitResponse;

import com.example.care4elders.model.Doctor;

import com.example.care4elders.services.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService  doctorService;
    private final PatientService patientService;
    private final FamilyService  familyService;
    private final AppointmentService appointmentService;
    private final VisitService visitService;


    @PostMapping("/create")
    public ResponseEntity<String> addDoctor(@RequestBody DoctorRequest request) {

        Doctor doctor = new Doctor();
        doctor.setDoctorname(request.getDoctorname()); // Don't forget to set the doctor's name!
        doctor.setAddress(request.getAddress());
        doctor.setPhonenumber(request.getPhonenumber());
        doctor.setGender(request.getGender());
        doctor.setHospital(request.getHospital());
        doctor.setMedicalID(request.getMedicalID());
        doctor.setPassword(request.getPassword()); // Hash this in a real app!
        doctor.setSpeciality(request.getSpeciality());
        doctor.setScore(request.getScore());

        doctorService.save(doctor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor added successfully with ID: " + doctor.getId());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> allDoctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(allDoctors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        return doctor.map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id-by-name")
    public ResponseEntity<Long> getDoctorIdByName(@RequestParam("name") String doctorName) {
        Optional<Long> doctorId = doctorService.getDoctorIdByDoctorName(doctorName);

        if (doctorId.isPresent()) {
            return ResponseEntity.ok(doctorId.get()); // Return 200 OK with the ID
        } else {
            // Return 404 Not Found if no doctor with that name is found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllDoctors() {
        try {
            doctorService.deleteAllDoctors();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("password/{updatepass}/{id}")
    public ResponseEntity<?> updatePassword(
            @PathVariable String updatepass,
            @PathVariable long id,
            @RequestParam String password) {
        try {
            Optional<Doctor> updated = Optional.ofNullable(doctorService.updatePassword(id,password));
            return ResponseEntity.ok("Password changed to " + password);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Doctor not found");
        }
    }

    @PostMapping("/auth") // This will be your endpoint for checking existence
    public ResponseEntity<Boolean> checkDoctorExists(@RequestBody CheckDoctorRequest request) {
        boolean exists = doctorService.checkDoctorExists(request.getId(), request.getMedicalID(), request.getPassword());
        return ResponseEntity.ok(exists); // Returns true or false
    }

    @PostMapping("/{id}/families/{familyId}")
    public ResponseEntity<Doctor> addFamilyToDoctor(
            @PathVariable Long id,
            @PathVariable Long familyId) {
        try {
            Doctor updatedDoctor = doctorService.addFamilyToDoctor(id, familyId);
            return ResponseEntity.ok(updatedDoctor); // Returns the updated Doctor entity
        } catch (EntityNotFoundException e) {
            // Return 404 Not Found if either Doctor or Family doesn't exist
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Catch any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{doctorId}/patients/{patientId}")
    public ResponseEntity<Doctor> addPatientToDoctor(
            @PathVariable Long doctorId,
            @PathVariable Long patientId) {
        try {
            Doctor updatedDoctor = doctorService.addPatientToDoctor(doctorId, patientId);
            return ResponseEntity.ok(updatedDoctor); // Returns the updated Doctor entity
        } catch (EntityNotFoundException e) {
            // Return 404 Not Found if either Doctor or Patient doesn't exist
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Catch any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // You might also want a DELETE endpoint to remove a patient from a doctor
    @DeleteMapping("/{doctorId}/patients/{patientId}")
    public ResponseEntity<Void> removePatientFromDoctor(
            @PathVariable Long doctorId,
            @PathVariable Long patientId) {
        try {
            doctorService.removePatientFromDoctor(doctorId, patientId);
            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{doctorId}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getDoctorAppointments(@PathVariable Long doctorId) {
        try {
            List<AppointmentResponse> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
            return ResponseEntity.ok(appointments); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found if doctor doesn't exist
        }
    }

    @DeleteMapping("/{doctorId}/appointments/{appointmentId}")
    public ResponseEntity<Doctor> removeAppointmentFromDoctor(
            @PathVariable Long doctorId,
            @PathVariable Long appointmentId) {
        try {
            Doctor updatedDoctor = doctorService.removeAppointmentFromDoctor(doctorId, appointmentId);
            return ResponseEntity.ok(updatedDoctor); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) { // For specific validation like "not assigned to this doctor"
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/families")
    public ResponseEntity<Doctor> getDoctorWithFamilies(@PathVariable Long id) {
        try {
            Doctor doctor = doctorService.getDoctorWithFamilies(id);
            return ResponseEntity.ok(doctor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{doctorId}/visits")
    public ResponseEntity<List<VisitResponse>> getDoctorVisits(@PathVariable Long doctorId) {
        try {
            List<VisitResponse> visits = visitService.getVisitsByDoctorId(doctorId);
            return ResponseEntity.ok(visits); // 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found if doctor doesn't exist
        }
    }

    @PostMapping("/{doctorId}/insurances/{insuranceId}")
    public ResponseEntity<DoctorResponse> addAffiliatedInsurance(
            @PathVariable Long doctorId,
            @PathVariable Long insuranceId) {
        try {
            DoctorResponse updatedDoctor = doctorService.addAffiliatedInsurance(doctorId, insuranceId);
            return ResponseEntity.ok(updatedDoctor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            // E.g., if insurance is already affiliated
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{doctorId}/insurances/{insuranceId}")
    public ResponseEntity<DoctorResponse> removeAffiliatedInsurance(
            @PathVariable Long doctorId,
            @PathVariable Long insuranceId) {
        try {
            DoctorResponse updatedDoctor = doctorService.removeAffiliatedInsurance(doctorId, insuranceId);
            return ResponseEntity.ok(updatedDoctor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            // E.g., if insurance was not affiliated
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Or Conflict, depending on desired strictness
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}





