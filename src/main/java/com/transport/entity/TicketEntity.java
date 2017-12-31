package com.transport.entity;

import lombok.Data;

@Data
public class TicketEntity {
    private int userId;
    private double price;
    private int courseId;
}
