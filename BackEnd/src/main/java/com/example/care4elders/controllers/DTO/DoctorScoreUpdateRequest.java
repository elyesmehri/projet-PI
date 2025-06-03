package com.example.care4elders.controllers.DTO;

public class DoctorScoreUpdateRequest {

    private String doctorname;
    private int score;


    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
