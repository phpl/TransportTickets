package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class RouteEntity {
    @NonNull
    private int distance;
    @NonNull
    private String beginCity;
    @NonNull
    private String endCity;
}
