package com.transport.view.lists;

import lombok.Data;
import lombok.NonNull;

@Data
public class UsersList {
    @NonNull
    int userId;
    @NonNull
    int personalDataId;
    @NonNull
    int addressId;
    @NonNull
    private String username;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private int phoneNumber;
    @NonNull
    private String city;
    @NonNull
    private String street;
    @NonNull
    private String houseNumber;
}
