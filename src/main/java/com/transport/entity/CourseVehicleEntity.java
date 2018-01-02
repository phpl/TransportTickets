package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class CourseVehicleEntity {
    @NonNull
    private int courseId;
    @NonNull
    private int vehicleId;
}
