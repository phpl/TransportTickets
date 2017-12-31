package com.transport.entity;

import lombok.Data;

@Data
public class VechicleEntity {
    private String model;
    private String licencePlate;
    private int seatNumber;
    private double acceptableLuggageWeight;
}
