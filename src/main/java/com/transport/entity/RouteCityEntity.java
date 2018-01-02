package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class RouteCityEntity {
    @NonNull
    private int routeId;
    @NonNull
    private int cityId;
}
