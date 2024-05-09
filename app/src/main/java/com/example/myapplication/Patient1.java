package com.example.myapplication;

import android.graphics.Bitmap;

public class Patient1 {
    private String patientId;
    private String name;
    private String gender;
    private String phno;
    private Bitmap profileImage; // Bitmap for profile image

    public Patient1(String patientId, String name, String gender, String phno, Bitmap profileImage) {
        this.patientId = patientId;
        this.name = name;
        this.gender = gender;
        this.phno = phno;
        this.profileImage = profileImage;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String gender() {
        return gender;
    }

    public String phno() {
        return phno;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }
}
