package com.transport.entity;

import lombok.Data;

import java.time.LocalTime;

@Data
public class CourseEntity {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int maxAvailableSeats;
    private int routeId;
}
