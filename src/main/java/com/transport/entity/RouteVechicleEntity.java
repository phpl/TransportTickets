package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class RouteVechicleEntity {
    @NonNull
    private int courseId;
    @NonNull
    private int vechicleId;
}
