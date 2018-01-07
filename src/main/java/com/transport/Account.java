package com.transport;

public class Account {
    public static Integer currentUserId = null;
    public static AccountType type = null;

    public enum AccountType {
        ADMINISTRATOR,
        USER,
        GUEST
    }
}

