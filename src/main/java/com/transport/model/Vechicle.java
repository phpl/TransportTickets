package com.transport.model;

import lombok.Data;

@Data
public class Vechicle {
    private String model;
    private String licencePlate;
    private int seatNumber;
    private double acceptableLuggageWeight;
}
