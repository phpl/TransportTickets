package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class VechicleEntity {
    @NonNull
    private String model;
    @NonNull
    private String licencePlate;
    @NonNull
    private int seatNumber;
    @NonNull
    private double acceptableLuggageWeight;
}
