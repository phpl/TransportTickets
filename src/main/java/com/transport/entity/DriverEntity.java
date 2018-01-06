package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class DriverEntity {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private int phoneNumber;
}
