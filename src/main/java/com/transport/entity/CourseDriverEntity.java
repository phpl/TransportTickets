package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class CourseDriverEntity {
    @NonNull
    private int driverId;
    @NonNull
    private int courseId;
}
