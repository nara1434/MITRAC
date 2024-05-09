package com.example.myapplication;

public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String sex;

    public Patient(String patientId, String name, int age, String sex) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }
}
