package com.transport.view.lists;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

@Data
public class ScheduleList {
    @NonNull
    private Integer courseId;
    @NonNull
    private Integer routeId;
    @NonNull
    private final String beginCity;
    @NonNull
    private final String endCity;
    @NonNull
    private LocalTime departureTime;
    @NonNull
    private Integer freeSeats;
    @NonNull
    private Integer distance;
    @NonNull
    private Integer ticketPrice;
}