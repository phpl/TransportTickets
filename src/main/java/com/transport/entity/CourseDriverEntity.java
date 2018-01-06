package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class CourseDriverEntity {
    @NonNull
    private int courseId;
    @NonNull
    private int driverId;
}
