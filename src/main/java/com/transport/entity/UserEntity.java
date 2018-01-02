package com.transport.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserEntity {
    @NonNull
    private String login;
    @NonNull
    private String password;
}
