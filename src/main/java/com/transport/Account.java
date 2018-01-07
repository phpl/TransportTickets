package com.transport;

public class Account {
    public static AccountType type = null;

    public enum AccountType {
        ADMINISTRATOR,
        USER,
        GUEST
    }
}

