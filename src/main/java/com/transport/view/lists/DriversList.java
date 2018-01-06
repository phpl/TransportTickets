package com.transport.view.lists;

import lombok.Data;
import lombok.NonNull;

@Data
public class DriversList {
    @NonNull
    private int driverId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private int phoneNumber;
    @NonNull
    private int courseId;
}
