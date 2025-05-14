package com.example.care4elders.controllers;

import java.time.LocalDateTime;


public class AppointmentRequest {

        public String doctorname;
        public String familyname;
        public LocalDateTime date;
        public Double tariff;
        public int nature;
        public Integer period;
        public int emergency;
        public Boolean quoted;
        public String description;
        public boolean skipped;
}
