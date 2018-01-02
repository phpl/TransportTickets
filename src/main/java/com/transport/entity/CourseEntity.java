package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

@Data
public class CourseEntity {
    @NonNull
    private LocalTime departureTime;
    @NonNull
    private LocalTime arrivalTime;
    @NonNull
    private int maxAvailableSeats;
    @NonNull
    private int routeId;
}
