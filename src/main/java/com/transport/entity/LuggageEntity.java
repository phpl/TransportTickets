package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class LuggageEntity {
    @NonNull
    private int ticketId;
    @NonNull
    private double weight;
    @NonNull
    private int userId;
}
