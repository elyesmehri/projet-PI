package com.example.care4elders.controllers;

import java.util.List;
import java.util.Map;

public class PillularRequest {

    public String patientName;
    public String doctorname;
    public String medicationName;
    public List<PosologyRequest> posology;  // Use a list of PosologyRequest

    public static class PosologyRequest {
        public String timeSlot;  // "matin", "midi", or "soir"
        public Boolean taken;    // true or false
    }
}
