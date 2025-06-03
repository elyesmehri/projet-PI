package com.example.care4elders.controllers.DTO;

import lombok.Getter;

@Getter
public class CheckFamilyRequest {
    private Long id;
    private String relative;
    private String password;

    // Default constructor (required for Jackson)
    public CheckFamilyRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
