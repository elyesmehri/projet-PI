package com.example.care4elders.controllers;

import com.example.care4elders.model.Doctor;
import com.example.care4elders.model.Patient;
import com.example.care4elders.services.DoctorService;
import com.example.care4elders.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.care4elders.controllers.DoctorPasswordUpdateRequest;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService  doctorService;
    private final PatientService patientService;


    @PostMapping("/bulk")
    public ResponseEntity<List<Doctor>> createBulkDoctor(@RequestBody List<Doctor> doctors) {
        try {
            List<Doctor> saved = doctorService.addDoctorsBulk(doctors);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/auth")
    public ResponseEntity<Boolean> authenticateDoctor(@RequestBody Map<String, String> payload) {
        String name = payload.get("doctorname");
        String password = payload.get("password");
        boolean result = doctorService.authenticateDoctor(name, password);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody DoctorPasswordUpdateRequest request) {
        boolean updated = doctorService.updatePassword(request.getDoctorname(), request.getPassword());

        if (updated) {
            return ResponseEntity.ok("Password updated successfully ✅");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Doctor not found");
        }
    }

    @GetMapping("/all")
    public List<Doctor> getAllDoctor() {
        return doctorService.getAllDoctors();
    }

    @PostMapping("/add")
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    @PutMapping("/update/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor updatedDoctor) {
        return doctorService.updateDoctor(id, updatedDoctor);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteBulkDoctor(@RequestBody List<Long> ids) {
        try {
            doctorService.deleteDoctorsBulk(ids);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteBulkDoctors() {
        try {
            doctorService.deleteAllDoctors();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patients")  // Changed the URL
    public ResponseEntity<List<Patient>> getPatientsByDoctor(
            @RequestParam("doctorName") String doctorName) { // Using @RequestParam
        List<Patient> patients = patientService.getPatientsByDoctor(doctorName);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PutMapping("/updateScore")
    public ResponseEntity<?> updateDoctorScore(@RequestBody DoctorScoreUpdateRequest request) {
        boolean updated = doctorService.updateScoreByName(request.getDoctorname(), request.getScore());

        if (updated) {
            return ResponseEntity.ok("✅ Score updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Doctor not found.");
        }
    }

    @GetMapping("/name/{doctorname}")
    public ResponseEntity<?> getDoctorByName(@PathVariable String doctorname) {
        Doctor doctor = doctorService.findByName(doctorname);
        System.out.println("Searching for doctor with name: " + doctorname);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found with name: " + doctorname);
        }
        return ResponseEntity.ok(doctor);
    }

}





