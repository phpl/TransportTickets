package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class PersonalDataEntity {
    @NonNull
    private int userId;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private int phoneNumber;
    @NonNull
    private int addresId;
}
