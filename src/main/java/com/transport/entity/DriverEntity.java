package com.transport.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DriverEntity {
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private LocalDate periodOfMedicalCheckUpValidation;
    private LocalDate periodOfDriverLicenceValidation;
}
