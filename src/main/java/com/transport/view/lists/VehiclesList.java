package com.transport.view.lists;

import lombok.Data;
import lombok.NonNull;

@Data
public class VehiclesList {
    @NonNull
    private int vehicleId;
    @NonNull
    private String model;
    @NonNull
    private String licencePlate;
    @NonNull
    private int seatsNumber;
    @NonNull
    private double luggageWeight;
    @NonNull
    private int courseId;
}
