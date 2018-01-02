package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class AddressEntity {
    @NonNull
    private String city;
    @NonNull
    private String street;
    @NonNull
    private String houseNumber;
}
