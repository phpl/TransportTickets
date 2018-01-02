package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class TicketEntity {
    @NonNull
    private int userId;
    @NonNull
    private double price;
    @NonNull
    private int courseId;
}
