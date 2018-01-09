package com.transport.view.lists;

import lombok.Data;
import lombok.NonNull;

@Data
public class PassengersList {
    @NonNull
    private Integer userId;
    @NonNull
    private Integer ticketId;
    @NonNull
    private Integer luggageId;
    @NonNull
    private Integer courseId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Integer phoneNumber;
    @NonNull
    private Double luggageWeight;
}
