package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class VehicleEntity {
    @NonNull
    private String model;
    @NonNull
    private String licencePlate;
    @NonNull
    private int seatsNumber;
}
