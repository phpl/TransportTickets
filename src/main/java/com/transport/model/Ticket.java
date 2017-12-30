package com.transport.model;

import lombok.Data;

@Data
public class Ticket {
    private int userId;
    private double price;
    private int courseId;
}
