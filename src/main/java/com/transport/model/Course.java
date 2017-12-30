package com.transport.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Course {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int maxAvailableSeats;
    private int routeId;
}
