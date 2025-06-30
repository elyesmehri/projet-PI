package com.example.care4elders.repository;

import com.example.care4elders.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Configures only JPA components, uses an in-memory database by default
@AutoConfigureTestDatabase(replace = Replace.ANY) // Ensures an in-memory database (like H2) is used
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    // Helper to create a Doctor object
    private Doctor createDoctor(String name, String medicalId) {
        Doctor doctor = new Doctor();
        doctor.setDoctorname(name);
        doctor.setMedicalID(medicalId);
        doctor.setPassword("testpass");
        doctor.setSpeciality("General");
        doctor.setAddress("Test Address");
        doctor.setScore(0);
        doctor.setPhonenumber("111-222-3333");
        doctor.setHospital("Test Hospital");
        doctor.setNumberofpatients(0);
        doctor.setGender(true);
        return doctor;
    }

    @BeforeEach
    void setUp() {
        // Clear the database before each test to ensure test isolation
        doctorRepository.deleteAll();
    }

    @Test
    void testSaveDoctor() {
        // Given
        Doctor doctor = createDoctor("Dr. Test", "TEST001");

        // When
        Doctor savedDoctor = doctorRepository.save(doctor);

        // Then
        assertNotNull(savedDoctor.getId());
        assertEquals("Dr. Test", savedDoctor.getDoctorname());
        assertEquals("TEST001", savedDoctor.getMedicalID());
    }

    @Test
    void testFindById() {
        // Given
        Doctor doctor = createDoctor("Dr. Find", "FIND002");
        doctorRepository.save(doctor);

        // When
        Optional<Doctor> foundDoctor = doctorRepository.findById(doctor.getId());

        // Then
        assertTrue(foundDoctor.isPresent());
        assertEquals("Dr. Find", foundDoctor.get().getDoctorname());
    }

    @Test
    void testFindAll() {
        // Given
        doctorRepository.save(createDoctor("Dr. A", "A1"));
        doctorRepository.save(createDoctor("Dr. B", "B2"));

        // When
        List<Doctor> doctors = doctorRepository.findAll();

        // Then
        assertNotNull(doctors);
        assertEquals(2, doctors.size());
    }

    @Test
    void testDeleteById() {
        // Given
        Doctor doctor = createDoctor("Dr. Delete", "DEL003");
        doctorRepository.save(doctor);
        Long doctorId = doctor.getId();

        // When
        doctorRepository.deleteById(doctorId);

        // Then
        assertFalse(doctorRepository.existsById(doctorId));
    }

    @Test
    void testExistsById() {
        // Given
        Doctor doctor = createDoctor("Dr. Exists", "EXISTS004");
        doctorRepository.save(doctor);
        Long doctorId = doctor.getId();

        // When
        boolean exists = doctorRepository.existsById(doctorId);
        boolean notExists = doctorRepository.existsById(999L);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void testFindByDoctorname() {
        // Given
        Doctor doctor = createDoctor("Dr. Custom", "CUST005");
        doctorRepository.save(doctor);

        // When
        Optional<Doctor> found = doctorRepository.findByDoctorname("Dr. Custom");
        Optional<Doctor> notFound = doctorRepository.findByDoctorname("NonExistentName");

        // Then
        assertTrue(found.isPresent());
        assertEquals("CUST005", found.get().getMedicalID());
        assertFalse(notFound.isPresent());
    }

    @Test
    void testDeleteAll() {
        // Given
        doctorRepository.save(createDoctor("Doc 1", "D1"));
        doctorRepository.save(createDoctor("Doc 2", "D2"));
        assertEquals(2, doctorRepository.count());

        // When
        doctorRepository.deleteAll();

        // Then
        assertEquals(0, doctorRepository.count());
    }
}
