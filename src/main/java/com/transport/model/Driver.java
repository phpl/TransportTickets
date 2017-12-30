package com.transport.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Driver {
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private LocalDate periodOfMedicalCheckUpValidation;
    private LocalDate periodOfDriverLicenceValidation;
}
