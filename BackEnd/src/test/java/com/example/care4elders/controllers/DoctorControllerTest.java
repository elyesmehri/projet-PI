package com.example.care4elders.controllers;

import com.example.care4elders.controllers.DTO.DoctorRequest;
import com.example.care4elders.model.Doctor;
import com.example.care4elders.services.DoctorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @Test
    void testGetAllDoctors() throws Exception {
        Doctor doc = new Doctor();
        doc.setDoctorname("Alice");

        Mockito.when(doctorService.getAllDoctors()).thenReturn(List.of(doc));

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorname").value("Alice"));
    }

    @Test
    void testGetDoctorById_Found() throws Exception {
        Doctor doc = new Doctor();
        doc.setId(1L);
        doc.setDoctorname("Bob");

        Mockito.when(doctorService.getDoctorById(1L)).thenReturn(Optional.of(doc));

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorname").value("Bob"));
    }

    @Test
    void testGetDoctorById_NotFound() throws Exception {
        Mockito.when(doctorService.getDoctorById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddDoctor() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(10L);
        doctor.setDoctorname("John");

        DoctorRequest req = new DoctorRequest();
        req.setDoctorname("John");

        Mockito.when(doctorService.save(Mockito.any())).thenReturn(doctor);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "doctorname": "John",
                            "address": "123 St",
                            "phonenumber": "123456789",
                            "gender": "Male",
                            "hospital": "General",
                            "medicalID": "ID123",
                            "password": "secret",
                            "speciality": "Cardiology",
                            "score": 4.5
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Doctor added successfully")));
    }

    @Test
    void testGetDoctorIdByName_Found() throws Exception {
        Mockito.when(doctorService.getDoctorIdByDoctorName("John")).thenReturn(Optional.of(10L));

        mockMvc.perform(get("/id-by-name").param("name", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    void testGetDoctorIdByName_NotFound() throws Exception {
        Mockito.when(doctorService.getDoctorIdByDoctorName("Unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/id-by-name").param("name", "Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAllDoctors() throws Exception {
        mockMvc.perform(delete("/delete/all"))
                .andExpect(status().isNoContent());

        Mockito.verify(doctorService).deleteAllDoctors();
    }
}
